package owl.humanresourcesmanagement.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import owl.humanresourcesmanagement.dto.request.*;
import owl.humanresourcesmanagement.dto.response.ForgotPasswordResponseDto;
import owl.humanresourcesmanagement.dto.response.LoginResponseDto;
import owl.humanresourcesmanagement.entity.*;
import owl.humanresourcesmanagement.enums.company.ECompanyState;
import owl.humanresourcesmanagement.enums.user.*;
import owl.humanresourcesmanagement.mapper.*;
import owl.humanresourcesmanagement.exception.*;
import owl.humanresourcesmanagement.exception.Exception;
import owl.humanresourcesmanagement.repository.*;
import owl.humanresourcesmanagement.utility.CodeGenerator;
import owl.humanresourcesmanagement.utility.JwtManager;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final CompanyRepository companyRepository;
	private final UserRepository userRepository;
	private final RefreshTokenRepository refreshTokenRepository;
	private final PasswordEncoder passwordEncoder;
	private final CodeGenerator codeGenerator;
	private final JwtManager jwtManager;
	private final MailSenderService mailSenderService;
	private final RefreshTokenService refreshTokenService;
	private final CompanyService companyService;
	private final TokenService tokenService;
	
	public Boolean register(RegisterRequestDto dto) {
		// Company Control (Current)
		Optional<Company> companyOptional = companyRepository.findByName(dto.companyName());
		if (companyOptional.isPresent()) {
			throw new Exception(ErrorType.ALREADY_EXIST_COMPANY);
		}
		
		controlCompanyDomain(dto);
		
		// Company Mail Control (Current)
		if (userRepository.existsByMail(dto.mail())) {
			throw new Exception(ErrorType.ALREADY_EXIST_USER_MAIL);
		}
		
		// Phone Control (Current)
		if (userRepository.existsByPhone(dto.phone())) {
			throw new Exception(ErrorType.ALREADY_EXIST_USER_PHONE);
		}
		
		// DTO → Entity
		User user = UserMapper.INSTANCE.fromRegisterDto(dto);
		user.setUserState(EUserState.PENDING);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		// Save Company
		Company company = companyService.createCompany(dto.companyName());
		user.getEmployeeInformation().setCompanyId(company.getId());
		
		// Save User (ID is set) Manager Created
		user.setRole(EUserRole.MANAGER);
		user = userRepository.save(user);
		
		// SaveEmployee (ID is set) User da bir employee. EmployeeInformation bilgileri
		User userMp = UserMapper.INSTANCE.fromRegisterDto(dto);
		userMp.setId(user.getId());      // userId’yi sonradan set ediyoruz
		userMp.getEmployeeInformation().setCompanyId(company.getId());
		userMp.getEmployeeInformation().setDateOfEmployment(dto.dateOfEmployment());
		userRepository.save(userMp);
		
		String generatedCode = codeGenerator.generateCode();
		ActivationCode activationCode = new ActivationCode();
		activationCode.setUserId(user.getId());
		activationCode.setCode(generatedCode);
		activationCode.setExpirationTime(LocalDateTime.now().plusMinutes(15));
		
		user.setActivationCode(activationCode);
		
		// Send Activation E-mail
		mailSenderService.sendMail(new MailSenderRequestDto(user.getMail(), generatedCode));
		return true;
	}
	
	private void controlCompanyDomain(RegisterRequestDto dto){
		// Mail Domain Name & Company Name Compatibility Control
		String companyNameNormalized = dto.companyName().trim().toLowerCase(Locale.ROOT);
		String actualMailNormalized = dto.mail().trim().toLowerCase(Locale.ROOT);
		
		// Get Domain Name Part - DP (Part After '@' Sign)
		int atIndex = actualMailNormalized.indexOf('@');
		if (atIndex < 0 || atIndex == actualMailNormalized.length() - 1) {
			throw new Exception(ErrorType.INVALID_COMPANY_MAIL_FORMAT); // Invalid E-mail Format
		}
		String domainAndTLD = actualMailNormalized.substring(atIndex + 1); // Ex: google.com etc.
		
		// Separate Top Level Domain Name - TLD (Get Between '@' and '.')
		int dotIndex = domainAndTLD.indexOf('.');
		String actualDomain;
		if (dotIndex > 0) {
			actualDomain = domainAndTLD.substring(0, dotIndex); // "google.com" → "google"
		} else {
			actualDomain = domainAndTLD; // If there is no '.' DP domain name is accepted
		}
		
		// Comparing Domain Name & Company Name
		if (!actualDomain.equals(companyNameNormalized)) {
			throw new Exception(ErrorType.MAIL_COMPANY_MISMATCH);
		}
	}
	
	public LoginResponseDto login(@Valid UserLoginRequestDto dto) {
		Optional<User> userOptional = userRepository.findOptionalByMail(dto.mail());
		
		if (userOptional.isEmpty()) {
			throw new Exception(ErrorType.INVALID_MAIL_OR_PASSWORD);
		}
		
		User user = userOptional.get();
		if(!passwordEncoder.matches(dto.password(), user.getPassword())){
			throw new Exception(ErrorType.INVALID_MAIL_OR_PASSWORD);
		}
		
		switch (user.getUserState()) {
			case PENDING -> throw new Exception(ErrorType.ACCOUNT_PENDING);
			case DENIED -> throw new Exception(ErrorType.ACCOUNT_DENIED);
			case INACTIVE -> throw new Exception(ErrorType.ACCOUNT_INACTIVE);
			case BANNED -> throw new Exception(ErrorType.ACCOUNT_BANNED);
			case ACTIVE -> {}
			default -> throw new Exception(ErrorType.ACCOUNT_DOESNT_ACTIVE); // Unexpected Status
		}
		
		//Company accepted ise login ol
		Long companyId = user.getEmployeeInformation().getCompanyId();
		Optional<Company> company = companyRepository.findById(companyId);
		if (company.isEmpty()) {
			throw new Exception(ErrorType.COMPANY_NOT_FOUND);
		}
		
		if(!company.get().getCompanyState().equals(ECompanyState.ACCEPTED)) {
			throw new Exception(ErrorType.COMPANY_NOT_ACCEPTED);
		}
		
		String accessToken = jwtManager.generateAccessToken(user.getId(), user.getRole().toString());
		
		String refreshToken = refreshTokenService.createRefreshToken(user.getId()).getToken();
		
		return new LoginResponseDto(accessToken, refreshToken, user.getRole());
	}
	
	public String activateCode(ActivateCodeRequestDto dto) {
		// Find User
		User user = userRepository.findById(dto.id())
		                          .orElseThrow(() -> new Exception(ErrorType.USER_NOT_FOUND));
		
		ActivationCode userCode = user.getActivationCode();
		if (userCode == null) {
			throw new Exception(ErrorType.ACTIVATION_CODE_MISMATCH);
		}
		
		// Check ExpirationTime
		LocalDateTime now = LocalDateTime.now();
		if (now.isAfter(userCode.getExpirationTime())) {
			throw new Exception(ErrorType.ACTIVATION_CODE_EXPIRED);
		}
		
		// Comparing Activation Code (Does code match)
		if (!userCode.getCode().equals(dto.activationCode())) {
			throw new Exception(ErrorType.ACTIVATION_CODE_MISMATCH);
		}
		
		// Status Control & Activation
		return statusControl(user);
	}
	
	public String statusControl(User user) {
		switch (user.getUserState()) {
			case ACTIVE -> throw new Exception(ErrorType.ACCOUNT_ALREADY_ACTIVE);
			case PENDING -> {
				user.setUserState(EUserState.ACTIVE);
				userRepository.save(user);
				return "Activation Successful! You can login to the system!";
			}
			case BANNED -> throw new Exception(ErrorType.ACCOUNT_BANNED);
			case INACTIVE -> throw new Exception(ErrorType.ACCOUNT_INACTIVE);
			case DENIED -> throw new Exception(ErrorType.ACCOUNT_DENIED);
			default -> throw new Exception(ErrorType.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ForgotPasswordResponseDto forgotPassword(String mail) {
		// Yeni bir sıfırlama kodu oluştur
		String resetCode = codeGenerator.generateCode();
		MailSenderRequestDto mailDto = new MailSenderRequestDto(mail, resetCode);
		
		// E-posta adresiyle kullanıcıyı bul. Kullanıcı bulunamazsa hata fırlat.
		User user = userRepository.findOptionalByMail(mail)
		                          .orElseThrow(() -> new Exception(ErrorType.USER_NOT_FOUND));
		
		if (user.getUserState() != EUserState.ACTIVE) {
			throw new Exception(ErrorType.ACCOUNT_DOESNT_ACTIVE);
		}
		
		// Kullanıcının mevcut bir parola sıfırlama kodu olup olmadığını kontrol et
		ResetPasswordCode existingResetPasswordCode = user.getResetPasswordCode();
		
		if (existingResetPasswordCode != null) {
			// Eğer mevcut bir kod varsa, bu kodun değerlerini güncelle (üzerine yaz)
			existingResetPasswordCode.setCode(resetCode);
			existingResetPasswordCode.setExpirationTime(LocalDateTime.now().plusMinutes(15));
		} else {
			// Eğer mevcut bir kod yoksa, yeni bir ResetPasswordCode nesnesi oluştur
			ResetPasswordCode newResetPasswordCode = ResetPasswordCode.builder()
			                                                          .code(resetCode)
			                                                          .expirationTime(LocalDateTime.now().plusMinutes(15))
			                                                          .user(user) // Yeni kodu kullanıcıyla ilişkilendir
			                                                          .build();
			user.setResetPasswordCode(newResetPasswordCode);
		}
		
		userRepository.save(user);
		
		// Oluşturulan sıfırlama kodunu içeren e-postayı gönder
		mailSenderService.sendPasswordMail(mailDto);
		
		String token = jwtManager.generateToken(user.getId());
		
		// Yanıt DTO'sunu döndür
		return new ForgotPasswordResponseDto(
				token,
				mail,
				"Şifre yenileme kodunuz e-posta adresinize gönderildi."
		);
	}
	
	public boolean resetPassword(ResetPasswordRequestDto dto) {
		User user = tokenService.getToken(dto.token());
		
		ResetPasswordCode resetPasswordCode = user.getResetPasswordCode();
		
		// Kod geçerli mi?  Eğer resetPasswordCode nesnesi hiç set edilmediyse
		if (resetPasswordCode == null) {
			throw new Exception(ErrorType.PASSWORD_RESET_CODE_MISMATCH);
		}
		
		// Süre dolmuş mu? (Expiration kontrolü)
		LocalDateTime now = LocalDateTime.now();
		if (now.isAfter(resetPasswordCode.getExpirationTime())) {
			throw new Exception(ErrorType.PASSWORD_RESET_CODE_EXPIRED);
		}
		
		// Kod eşleşiyor mu? (Kod karşılaştırması)
		if (!resetPasswordCode.getCode().equals(dto.resetCode())) {
			throw new Exception(ErrorType.PASSWORD_RESET_CODE_MISMATCH);
		}
		
		user.setPassword(passwordEncoder.encode(dto.newPassword()));
		user.setResetPasswordCode(null); // Kod geçersiz kılınır
		userRepository.save(user);
		return true;
	}
	
	public void logout(String token) {
		jwtManager.deleteRefreshToken(token);
	}
	
	public String refreshAccessToken(String refreshToken) {
		RefreshToken token = refreshTokenRepository.findByToken(refreshToken)
		                                        .orElseThrow(() -> new Exception(ErrorType.INVALID_REFRESH_TOKEN));
		
		if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
			throw new Exception(ErrorType.EXPIRED_REFRESH_TOKEN);
		}
		
		User user = userRepository.findById(token.getAuthId())
		                          .orElseThrow(() -> new Exception(ErrorType.USER_NOT_FOUND));
		
		return jwtManager.generateAccessToken(user.getId(),user.getRole().toString());
	}

}
