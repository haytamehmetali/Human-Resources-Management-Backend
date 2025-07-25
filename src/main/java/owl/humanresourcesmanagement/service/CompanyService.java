package owl.humanresourcesmanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import owl.humanresourcesmanagement.dto.request.EmployeeRequestDto;
import owl.humanresourcesmanagement.dto.response.*;
import owl.humanresourcesmanagement.entity.Company;
import owl.humanresourcesmanagement.entity.User;
import owl.humanresourcesmanagement.enums.company.ECompanyState;
import owl.humanresourcesmanagement.enums.user.EUserRole;
import owl.humanresourcesmanagement.enums.user.EUserState;
import owl.humanresourcesmanagement.exception.*;
import owl.humanresourcesmanagement.exception.Exception;
import owl.humanresourcesmanagement.mapper.UserMapper;
import owl.humanresourcesmanagement.repository.CompanyRepository;
import owl.humanresourcesmanagement.repository.UserRepository;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {
	private final CompanyRepository companyRepository;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final TokenService tokenService;
	private final MemberShipService memberShipService;
	private final PublicHolidayService publicHolidayService;
	
	public Company createCompany(String companyName) {
		Optional<Company> companyOptional = companyRepository.findByName(companyName);
		if (companyOptional.isPresent()) {
			memberShipService.createOrFindMemberShip(companyOptional.get().getId());
			return companyOptional.get();
		}
		
		Company company = Company.builder()
		                         .name(companyName)
		                         .companyState(ECompanyState.PENDING)
		                         .build();
		company = companyRepository.save(company);
		memberShipService.createMemberShip(company.getId());
		return company;
	}
	
	public CompanyDashboardResponseDto getCompanyDashboard(String token) {
		User user = tokenService.getToken(token);
		List<PublicHolidayResponseDto> holidayDto = publicHolidayService.findAll();
		
		Long companyId = user.getEmployeeInformation().getCompanyId();
		Optional<Company> optionalCompany = companyRepository.findById(companyId);
		String company_name = "COMPANY DASHBOARD";
		
		if (optionalCompany.isEmpty()) {
			throw new Exception(ErrorType.COMPANY_NOT_FOUND);
		} else
			company_name = optionalCompany.get().getName();
		
		return  CompanyDashboardResponseDto.of(
				company_name,
				userRepository.countActivePersonalByCompanyId(companyId),
				userRepository.countApprovedPermissionsTodayByCompanyId(companyId),
				holidayDto
		);
	}
	
	public Page<EmployeeResponseDto> getEmployeeInCompany(String token, Pageable pageable) {
		User user = tokenService.getToken(token);
		
		if (!user.getRole().equals(EUserRole.MANAGER)) {
			throw new Exception(ErrorType.UNAUTHORIZED_OPERATION);
		}
		
		if (user.getEmployeeInformation().getCompanyId() == null) {
			throw new Exception(ErrorType.COMPANY_OR_EMPLOYEE_NOT_FOUND);
		}
		
		// Şirketteki tüm userId'leri al
		List<Long> userIds = userRepository.findUserIdByCompanyId(user.getEmployeeInformation().getCompanyId());
		
		Page<User> byIdIn = userRepository.findByIdIn(userIds, pageable);
		return byIdIn.map(UserMapper.INSTANCE::toDto);
	}
	
	public EmployeeDetailsResponseDto getEmployeeDetailsById(String token, Long id) {
		if(!tokenService.getToken(token).getRole().equals(EUserRole.MANAGER)) {
			throw new Exception(ErrorType.UNAUTHORIZED_OPERATION);
		}
		
		if (tokenService.getToken(token).getEmployeeInformation().getCompanyId() == null) {
			throw new Exception(ErrorType.COMPANY_OR_EMPLOYEE_NOT_FOUND);
		}
		
		List<Long> userIdsByCompanyId = userRepository.findUserIdByCompanyId(tokenService.getToken(token).getEmployeeInformation().getCompanyId());
		
		if (!userIdsByCompanyId.contains(id)) {
			throw new Exception(ErrorType.EMPLOYEE_NOT_FOUND);
		}
		
		Optional<User> employeeInCompany = userRepository.findById(id);
		if (employeeInCompany.isEmpty()) {
			throw new Exception(ErrorType.EMPLOYEE_NOT_FOUND);
		}
		
		return UserMapper.INSTANCE.toDetailsDto(employeeInCompany.get());
	}
	
	public EmployeeResponseDto addEmployee(String token, EmployeeRequestDto dto) {
		User manager = tokenService.getToken(token);
		
		if (manager.getRole() != EUserRole.MANAGER) {
			throw new Exception(ErrorType.UNAUTHORIZED_OPERATION);
		}
		
		if (userRepository.existsByMail(dto.mail())) {
			throw new Exception(ErrorType.ALREADY_EXIST_USER_MAIL);
		}
		
		User user = UserMapper.INSTANCE.fromEmployeeRequestDto(dto);
		user.setPassword(passwordEncoder.encode(dto.password()));
		user.getEmployeeInformation().setCompanyId(manager.getEmployeeInformation().getCompanyId());
		user.setRole(EUserRole.PERSONAL);
		user.setUserState(EUserState.ACTIVE);
		userRepository.save(user);
		
		// ⬇️ Burada mapper ile dönüştürme yapılıyor
		User userMp = UserMapper.INSTANCE.fromDto(dto);
		userMp.getEmployeeInformation().setId(user.getId());
		
		userRepository.save(userMp);
		return UserMapper.INSTANCE.toDto(userMp);
	}
	
	public Boolean makeActivePersonal(String token, Long id) {
		User user = validateManagerAndEmployeeAccess(token, id);
		
		// EmployeeInformation için aktiflik mevcut mu değil mi kontrolü
		if (user.getUserState() == (EUserState.ACTIVE)) {
			throw new Exception(ErrorType.EMPLOYEE_ALREADY_EXIST_ACTIVE);
		}
		
		// EmployeeInformation pending modda mı değil mi kontrol edilir.BURAYA BAK.
		if(user.getUserState() != (EUserState.PENDING)) {
			throw new Exception(ErrorType.EMPLOYEE_DOESNT_PENDING);
		}
		
		// Tüm şartları sağlıyorsa employee durumu aktif yapıyoruz
		user.setUserState(EUserState.ACTIVE);
		return true;
	}
	
	private User validateManagerAndEmployeeAccess(String token, Long id) {
		User users = validateManagerForUserAccess(token);
		
		// EmployeeInformation için kayıtlı personel kontrolü
		User user = userRepository.findById(users.getId())
		                       .orElseThrow(() -> new Exception(ErrorType.EMPLOYEE_NOT_FOUND));
		// Manager için employee üzerindeki yetki kontrülü
		if (!users.getEmployeeInformation().getCompanyId().equals(users.getEmployeeInformation().getCompanyId())) {
			throw new Exception(ErrorType.UNAUTHORIZED_OPERATION);
		}
		
		return user;
	}
	
	private User validateManagerForUserAccess(String token) {
		User users = tokenService.getToken(token);
		
		// Company için aktiflik kontrolü
		//if (!users.getCompanyId().equals(ECompanyState.ACCEPTED)) {
		//	throw new HRAppException(ErrorType.USER_COMPANY_STATE_DOESNT_ACCEPTED);
		//}
		
		// Token'a sahip user için aktiflik kontrolü
		if (users.getUserState() != EUserState.ACTIVE) {
			throw new Exception(ErrorType.USER_STATE_DOESNT_ACTIVE);
		}
		// User manager mı değil mi kontrolü
		if (users.getRole() != EUserRole.MANAGER) {
			throw new Exception(ErrorType.USER_NOT_MANAGER);
		}
		
		return users;
	}
	
	public Boolean makePassivePersonal(String token, Long id) {
		User user = validateManagerAndEmployeeAccess(token, id);
		
		// EmployeeInformation için pasiflik mevcut mu değil mi kontrolü
		if (user.getUserState() == (EUserState.INACTIVE)) {
			throw new Exception(ErrorType.EMPLOYEE_ALREADY_EXIST_INACTIVE);
		}
		
		// EmployeeInformation pending modda mı değil mi kontrol edilir
		if(user.getUserState() != (EUserState.PENDING)) {
			throw new Exception(ErrorType.EMPLOYEE_DOESNT_PENDING);
		}
		
		// Tüm şartları sağlıyorsa employee durumu pasif yapıyoruz
		user.setUserState(EUserState.INACTIVE);
		
		return true;
	}
	
	public Boolean changePersonalStatus(String token, Long id, EUserState newUserState) {
		User user = validateManagerAndEmployeeAccess(token, id);
		
		// EmployeeInformation için durum değişikliği kontrolü - aynı değer girilmemeli
		if(user.getUserState() == newUserState) {
			throw new Exception(ErrorType.USER_STATE_SAME);
		}
		user.setUserState(newUserState);
		
		return true;
	}
	
	public List<PersonalStateResponseDto> getActivePersonal(String token) {
		User usersValid = validateManagerForUserAccess(token);
		Long companyId = usersValid.getEmployeeInformation().getCompanyId();
		
		List<User> activeUsersByCompany = userRepository.findAllByEmployeeInformationCompanyIdAndUserState(companyId, EUserState.ACTIVE);
		if(activeUsersByCompany.isEmpty()) {
			throw new Exception(ErrorType.EMPLOYEE_NOT_FOUND);
		}
		
		return activeUsersByCompany.stream()
		                           .map(user -> new PersonalStateResponseDto(
				                           user.getId(),
				                           user.getEmployeeInformation().getFirstName(),
				                           user.getEmployeeInformation().getLastName(),
				                           user.getUserState()
		                           )).toList();
	}
	
	public List<PersonalStateResponseDto> getPassivePersonal(String token) {
		User usersValid = validateManagerForUserAccess(token);
		Long companyId = usersValid.getEmployeeInformation().getCompanyId();
		
		List<User> passiveUsersByCompany = userRepository.findAllByEmployeeInformationCompanyIdAndUserState(companyId, EUserState.INACTIVE);
		if(passiveUsersByCompany.isEmpty()) {
			throw new Exception(ErrorType.EMPLOYEE_NOT_FOUND);
		}
		
		return passiveUsersByCompany.stream()
		                            .map(user -> new PersonalStateResponseDto(
				                            user.getId(),
				                            user.getEmployeeInformation().getFirstName(),
				                            user.getEmployeeInformation().getLastName(),
				                            user.getUserState()
		                            )).toList();
	}
	
	public List<PersonalStateResponseDto> getPendingPersonal(String token) {
		User usersValid = validateManagerForUserAccess(token);
		Long companyId = usersValid.getEmployeeInformation().getCompanyId();
		
		List<User> pendingUsersByCompany = userRepository.findAllByEmployeeInformationCompanyIdAndUserState(companyId, EUserState.PENDING);
		if(pendingUsersByCompany.isEmpty()) {
			throw new Exception(ErrorType.EMPLOYEE_NOT_FOUND);
		}
		
		return pendingUsersByCompany.stream()
		                            .map(user -> new PersonalStateResponseDto(
				                            user.getId(),
				                            user.getEmployeeInformation().getFirstName(),
				                            user.getEmployeeInformation().getLastName(),
				                            user.getUserState()
		                            )).toList();
	}
	
	public List<PersonalStateResponseDto> getDeletedPersonal(String token) {
		User usersValid = validateManagerForUserAccess(token);
		Long companyId = usersValid.getEmployeeInformation().getCompanyId();
		
		List<User> deletedUsersByCompany = userRepository.findAllByEmployeeInformationCompanyIdAndUserState(companyId, EUserState.DELETED);
		if(deletedUsersByCompany.isEmpty()) {
			throw new Exception(ErrorType.EMPLOYEE_NOT_FOUND);
		}
		
		return deletedUsersByCompany.stream()
		                            .map(user -> new PersonalStateResponseDto(
				                            user.getId(),
				                            user.getEmployeeInformation().getFirstName(),
				                            user.getEmployeeInformation().getLastName(),
				                            user.getUserState()
		                            )).toList();
	}
	
	public Boolean makeDeletedPersonal(String token, Long id) {
		User user = validateManagerAndEmployeeAccess(token, id);
		
		// EmployeeInformation silinmiş mi kontrol edilir
		if (user.getUserState() == EUserState.DELETED) {
			throw new Exception(ErrorType.EMPLOYEE_ALREADY_EXIST_DELETED);
		}
		
		// Tüm şartları sağlıyorsa employee durumu pasif yapıyoruz
		user.setUserState(EUserState.DELETED);
		return true;
	}
	
}
