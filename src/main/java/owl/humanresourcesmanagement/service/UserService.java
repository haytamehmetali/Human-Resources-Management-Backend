package owl.humanresourcesmanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import owl.humanresourcesmanagement.dto.request.*;
import owl.humanresourcesmanagement.dto.response.*;
import owl.humanresourcesmanagement.entity.*;
import owl.humanresourcesmanagement.enums.permissions.*;
import owl.humanresourcesmanagement.enums.user.*;
import owl.humanresourcesmanagement.exception.Exception;
import owl.humanresourcesmanagement.exception.*;
import owl.humanresourcesmanagement.mapper.*;
import owl.humanresourcesmanagement.repository.*;
import owl.humanresourcesmanagement.utility.CodeGenerator;
import owl.humanresourcesmanagement.utility.JwtManager;
import java.time.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
	private final TokenService tokenService;
	private final UserRepository userRepository;
	private final PermissionRepository permissionRepository;
	private final PublicHolidaysRepository publicHolidaysRepository;
	private final PasswordEncoder passwordEncoder;
	private final MailSenderService mailSenderService;
	private final CodeGenerator codeGenerator;
	private final JwtManager jwtManager;
	private final SpendingService spendingService;
	private final EmbezzlementService embezzlementService;
	private final ShiftService shiftService;
	
	public EmployeeResponseDto updatePersonalDetails(String token, Long id, EmployeeUpdateRequestDto dto) {
		User manager = tokenService.getToken(token);
		
		if(!manager.getRole().equals(EUserRole.MANAGER)){
			throw new Exception(ErrorType.UNAUTHORIZED_OPERATION);
		}
		
		User user = userRepository.findById(id)
		                          .orElseThrow(() -> new Exception(ErrorType.EMPLOYEE_NOT_FOUND));
		
		if(!manager.getEmployeeInformation().getCompanyId().equals(user.getEmployeeInformation().getCompanyId())) {
			throw new Exception(ErrorType.UNAUTHORIZED_OPERATION);
		}
		
		UserMapper.INSTANCE.updateEmployeeFromDto(dto, user);
		
		User updated = userRepository.save(user);
		
		return UserMapper.INSTANCE.toDto(updated);
	}
	
	public UserProfileResponseDto getProfile(String token) {
		User user = tokenService.getToken(token);
		return UserMapper.INSTANCE.toUserProfileResponseDTO(user);
	}
	
	public UserProfileResponseDto updateProfile(String token, UpdateProfileRequestDto dto) {
		// Validate the token and get the user ID
		Optional<Long> userId = jwtManager.validateToken(token);
		if (userId.isEmpty()) {
			throw new Exception(ErrorType.INVALID_TOKEN);
		}
		
		// Find the user by ID
		User user = userRepository.findById(userId.get())
		                          .orElseThrow(() -> new Exception(ErrorType.USER_NOT_FOUND));
		
		// Check if the user account is active
		if (user.getUserState() != EUserState.ACTIVE) {
			throw new Exception(ErrorType.ACCOUNT_DOESNT_ACTIVE);
		}
		
		// Update the user entity with the new data from the DTO
		UserMapper.INSTANCE.updateUserFromDto(dto, user);
		
		// Save the updated user
		userRepository.save(user);
		
		// Return the response DTO
		return UserMapper.INSTANCE.toUserProfileResponseDTO(user);
	}
	
	public Boolean changeMail(String token, ChangeMailRequestDto dto) {
		// Token'dan kullanıcıyı al
		User user = tokenService.getToken(token);
		
		// Yeni e-posta mevcut e-posta ile aynıysa hata fırlat
		if (user.getMail().equals(dto.newMail())) {
			throw new Exception(ErrorType.MAIL_SAME);
		}
		
		// Yeni e-posta adresi zaten alınmışsa hata fırlat
		if (userRepository.existsByMail(dto.newMail())) {
			throw new Exception(ErrorType.MAIL_ALREADY_TAKEN);
		}
		
		// @ten sonrasını alarak domain karşılaştırması
		String currentEmailDomain = user.getMail().split("@")[1]; // Mevcut e-posta domaini
		String newEmailDomain = dto.newMail().split("@")[1]; // Yeni e-posta domaini
		
		// Domainler eşleşmiyorsa hata fırlat
		if (!currentEmailDomain.equals(newEmailDomain)) {
			throw new Exception(ErrorType.MAIL_COMPANY_MISMATCH);  // Domainler farklıysa hata fırlat
		}
		
		// Yeni e-posta adresi geçerli ise, e-posta değişikliği için kod üret
		// Kullanıcıyı güncelle
		String generatedCode = codeGenerator.generateCode();
		ChangeMailCode changeMailCode = new ChangeMailCode();
		changeMailCode.setUserId(user.getId());
		changeMailCode.setCode(generatedCode);
		changeMailCode.setExpirationTime(LocalDateTime.now().plusMinutes(15));
		
		user.setChangeMailCode(changeMailCode);
		user.setPendingMail(dto.newMail());
		// Send Activation E-mail
		mailSenderService.sendMail(new MailSenderRequestDto(user.getMail(), generatedCode));
		
		// Kullanıcıyı kaydet
		userRepository.save(user);
		
		// Aktivasyon kodu içeren e-posta gönder
		mailSenderService.sendMail(new MailSenderRequestDto(dto.newMail(), generatedCode));
		
		return true;
	}
	
	public void confirmEmailChange(String token, MailVerifyCodeRequestDto dto) {
		User user = tokenService.getToken(token);
		
		ChangeMailCode userCode = user.getChangeMailCode();
		if (userCode == null) { //Ayrı hata verebiliriz. Henüz code gönderilmedi
			throw new Exception(ErrorType.CHANGE_MAIL_CODE_MISMATCH);
		}
		
		// Check ExpirationTime
		LocalDateTime now = LocalDateTime.now();
		if (now.isAfter(userCode.getExpirationTime())) {
			throw new Exception(ErrorType.CHANGE_MAIL_CODE_EXPIRED);
		}
		
		// Comparing Activation Code (Does code match)
		if (!userCode.getCode().equals(dto.verificationCode())) {
			throw new Exception(ErrorType.CHANGE_MAIL_CODE_MISMATCH);
		}
		
		user.setMail(user.getPendingMail());
		user.setPendingMail(null);
		user.setChangeMailCode(null);
		userRepository.save(user);
	}
	
	public Boolean changePassword(String token, ChangePasswordRequestDto dto) {
		User user = tokenService.getToken(token);
		
		// Old password check
		if (!passwordEncoder.matches(dto.oldPassword(), user.getPassword())) {
			throw new Exception(ErrorType.PASSWORD_MISMATCH);
		}
		
		// Yeni şifre eski şifre kontrol
		if (passwordEncoder.matches(dto.newPassword(), user.getPassword())) {
			throw new Exception(ErrorType.PASSWORD_SAME);
		}
		
		user.setPassword(passwordEncoder.encode(dto.newPassword()));
		userRepository.save(user);
		return true;
	}
	
	public void deActivateAccount(String token, Long userId) {
		User user = tokenService.getToken(token);
		if(user.getRole().equals(EUserRole.PERSONAL)){
			throw new Exception(ErrorType.DELETED_ERROR_NOT_AUTH);
		}
		if(user.getRole().equals(EUserRole.MANAGER)){
			Optional<User> personalOptional = userRepository.findById(userId);
			if(personalOptional.isEmpty()){
				throw new Exception(ErrorType.USER_NOT_FOUND);
			}
			User personal = personalOptional.get();
			if (!Objects.equals(user.getEmployeeInformation().getCompanyId(), personal.getEmployeeInformation().getCompanyId())) {
				throw new Exception(ErrorType.DELETED_ERROR_NOT_AUTH);
			}
			personal.setUserState(EUserState.INACTIVE);
			userRepository.save(personal);
		}
	}
	
	public EmployeeDashboardResponseDto getEmployeeDashboard(String token) {
		User userFromToken = tokenService.getToken(token);
		
		if (userFromToken.getRole() != EUserRole.PERSONAL) {
			throw new Exception(ErrorType.USER_NOT_PERSONAL);
		}
		
		return UserMapper.INSTANCE.toEmployeeDashboardResponse(
				//publicHolidayService.findAll(), // List<PublicHolidayResponseDto>
				publicHolidaysRepository.findAll().stream().map(PublicHolidayResponseDto::fromEntity).toList(),
				getAnnualLeaveDetailsDto(userFromToken), // AnnualLeaveDetailsDto
				embezzlementService.getMyEmbezzlement(token), // List<EmbezzlementProductDetailResponseDto>
				spendingService.getMonthlySummary(token, LocalDate.now().getYear(), LocalDate.now().getMonthValue()), // PersonalSpendingSummaryWithTotalResponseDto
				shiftService.getMyShift(token) // List<My ShiftResponseDto>
		);
	}
	
	private AnnualLeaveDetailsDto getAnnualLeaveDetailsDto(User user) {
		int totalLeaves = EPermissionPolicy.getAnnualLeaveDays(user.getEmployeeInformation().getDateOfEmployment());
		
		// Bu yılın başı ve sonu
		LocalDate startOfYear = LocalDate.now().withDayOfYear(1);
		LocalDate endOfYear = LocalDate.now().withMonth(12).withDayOfMonth(31);
		
		List<Permission> usedLeaves = permissionRepository.findAllByUserIdAndPermissionTypeAndPermissionStateAndBeginDateBetween(
				user.getId(),
				EPermissionType.ANNUAL_LEAVE,
				EPermissionState.APPROVED,
				startOfYear,
				endOfYear
		);
		
		// Gün toplamı
		int used_leaves =  usedLeaves.stream()
		                             .mapToInt(p -> (int) (p.getEndDate().toEpochDay() - p.getBeginDate().toEpochDay() + 1)) // her iki gün dahil
		                             .sum();
		int remainingLeaves = totalLeaves - used_leaves;
		
		return new AnnualLeaveDetailsDto(totalLeaves, used_leaves, remainingLeaves);
	}
	
	public EmployeeResponseDto updateProfile(String token, EmployeeUpdateProfileRequestDto dto) {
		User employee_user = tokenService.getToken(token);
		
		User user = userRepository.findById(employee_user.getId())
		                          .orElseThrow(() -> new Exception(ErrorType.EMPLOYEE_NOT_FOUND));
		
		UserMapper.INSTANCE.updateEmployeeFromUpdateProfileDto(dto, user);
		
		User updated = userRepository.save(user);
		
		return UserMapper.INSTANCE.toDto(updated);
	}
}
