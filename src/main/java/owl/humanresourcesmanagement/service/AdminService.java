package owl.humanresourcesmanagement.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import owl.humanresourcesmanagement.dto.request.AdminLoginRequestDto;
import owl.humanresourcesmanagement.dto.request.ChangeCompanyStatusRequestDto;
import owl.humanresourcesmanagement.dto.request.IsAcceptedCompanyRequestDto;
import owl.humanresourcesmanagement.dto.response.AdminDashboardResponseDto;
import owl.humanresourcesmanagement.dto.response.AdminLoginResponseDto;
import owl.humanresourcesmanagement.dto.response.CompanyStateResponseDto;
import owl.humanresourcesmanagement.entity.Admin;
import owl.humanresourcesmanagement.entity.Company;
import owl.humanresourcesmanagement.entity.RefreshToken;
import owl.humanresourcesmanagement.entity.User;
import owl.humanresourcesmanagement.enums.EAdminRole;
import owl.humanresourcesmanagement.enums.EState;
import owl.humanresourcesmanagement.enums.company.ECompanyState;
import owl.humanresourcesmanagement.exception.*;
import owl.humanresourcesmanagement.exception.Exception;
import owl.humanresourcesmanagement.mapper.CompanyMapper;
import owl.humanresourcesmanagement.repository.AdminRepository;
import owl.humanresourcesmanagement.repository.CompanyRepository;
import owl.humanresourcesmanagement.repository.RefreshTokenRepository;
import owl.humanresourcesmanagement.repository.UserRepository;
import owl.humanresourcesmanagement.utility.JwtManager;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
	private final AdminRepository adminRepository;
	private final CompanyRepository companyRepository;
	private final UserRepository userRepository;
	private final RefreshTokenRepository refreshTokenRepository;
	private final RefreshTokenService refreshTokenService;
	private final JwtManager jwtManager;
	private final PasswordEncoder passwordEncoder;
	
	public AdminDashboardResponseDto getAdminDashboard(String token) {
		getAdminFromToken(token);
		return AdminDashboardResponseDto.of(
				"Admin Dashboard",
				companyRepository.countCompanies(),
				companyRepository.countActiveCompanies(),
				userRepository.countPersonal(),
				userRepository.countActivePersonal()
		);
	}
	
	public List<CompanyStateResponseDto> listAllPendingCompanies(String token) {
		getAdminFromToken(token);
		return CompanyMapper.INSTANCE.toStateDtoList(companyRepository.findAllByCompanyState(ECompanyState.PENDING));
	}
	
	public List<CompanyStateResponseDto> listAllAcceptedCompanies(String token) {
		getAdminFromToken(token);
		return CompanyMapper.INSTANCE.toStateDtoList(companyRepository.findAllByCompanyState(ECompanyState.ACCEPTED));
	}
	
	public List<CompanyStateResponseDto> listAllDeniedCompanies(String token) {
		getAdminFromToken(token);
		return CompanyMapper.INSTANCE.toStateDtoList(companyRepository.findAllByCompanyState(ECompanyState.DENIED));
	}
	
	public List<CompanyStateResponseDto> listAllDeletedCompanies(String token) {
		getAdminFromToken(token);
		return CompanyMapper.INSTANCE.toStateDtoList(companyRepository.findAllByCompanyState(ECompanyState.DELETED));
	}
	
	public List<CompanyStateResponseDto> listAllFindAllCompanies(String token) {
		getAdminFromToken(token);
		return CompanyMapper.INSTANCE.toStateDtoList(companyRepository.findAll());
	}
	
	public Boolean changeCompanyStatus(String token, ChangeCompanyStatusRequestDto dto) {
		getAdminFromToken(token);
		
		Company company = companyRepository.findById(dto.companyId())
		                                   .orElseThrow(() -> new Exception(ErrorType.COMPANY_NOT_FOUND));
		
		if (company.getCompanyState() == dto.newStatus()) {
			throw new Exception(ErrorType.COMPANY_STATE_SAME);
		}
		
		company.setCompanyState(dto.newStatus());
		return true;
	}
	
	public Boolean IsAcceptedCompany(String token, IsAcceptedCompanyRequestDto dto) {
		getAdminFromToken(token);
		
		Company company = companyRepository.findById(dto.id())
		                                .orElseThrow(() -> new Exception(ErrorType.COMPANY_NOT_FOUND));
		
		if (!company.getCompanyState().equals(ECompanyState.PENDING)) {
			throw new Exception(ErrorType.COMPANY_DOESNT_PENDING);
		}
		
		company.setCompanyState(dto.isAccepted() ? ECompanyState.ACCEPTED : ECompanyState.DENIED);
		companyRepository.save(company);
		return dto.isAccepted();
	}
	
	public void deActivateAccount(String token) {
		Admin admin = getAdminFromToken(token);
		
		if (admin.getAdminRole().equals(EAdminRole.SUPER_ADMIN)) {
			throw new Exception(ErrorType.SUPER_ADMIN_NOT_DELETED);
		}
		
		if (admin.getState().equals(EState.PASSIVE)) {
			throw new Exception(ErrorType.ACCOUNT_ALREADY_PASSIVE);
		}
		
		admin.setState(EState.PASSIVE);
	}
	
	public String refreshAccessToken(String refreshToken) {
		RefreshToken token = refreshTokenRepository.findByToken(refreshToken)
		                                        .orElseThrow(() -> new Exception(ErrorType.INVALID_REFRESH_TOKEN));
		
		if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
			throw new Exception(ErrorType.EXPIRED_REFRESH_TOKEN);
		}
		
		Admin admin = adminRepository.findById(token.getAuthId())
				.orElseThrow(() -> new Exception(ErrorType.USER_NOT_FOUND));
		
		return jwtManager.generateAccessToken(admin.getId(), admin.getAdminRole().toString());
	}
	
	public void logout(String token) {
		jwtManager.deleteRefreshToken(token);
	}
	
	public AdminLoginResponseDto login(@Valid AdminLoginRequestDto dto) {
		Admin admin = adminRepository.findOptionalByUsername(dto.username())
		                             .orElseThrow(() -> new Exception(ErrorType.INVALID_USERNAME_OR_PASSWORD));
		
		if (!admin.getState().equals(EState.ACTIVE)) {
			throw new Exception(ErrorType.ACCOUNT_DOESNT_ACTIVE);
		}
		
		if (!passwordEncoder.matches(dto.password(), admin.getPassword())) {
			throw new Exception(ErrorType.INVALID_USERNAME_OR_PASSWORD);
		}
		
		String accessToken = jwtManager.generateAccessToken(admin.getId(), admin.getAdminRole().toString());
		String refreshToken = refreshTokenService.createRefreshToken(admin.getId()).getToken();
		
		return new AdminLoginResponseDto(accessToken, refreshToken, admin.getAdminRole());
	}
	
	public Admin getAdminFromToken(String token) {
		Long adminId = jwtManager.validateToken(token)
		                         .orElseThrow(() -> new Exception(ErrorType.INVALID_TOKEN));
		
		return adminRepository.findById(adminId)
		                      .orElseThrow(() -> new Exception(ErrorType.ADMIN_NOT_FOUND));
	}
}
