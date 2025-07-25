package owl.humanresourcesmanagement.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import owl.humanresourcesmanagement.dto.request.CreateLeaveRequestDto;
import owl.humanresourcesmanagement.dto.request.IsApprovedRequestLeaveRequestDto;
import owl.humanresourcesmanagement.dto.response.AnnualLeaveDetailsDto;
import owl.humanresourcesmanagement.entity.*;
import owl.humanresourcesmanagement.enums.permissions.*;
import owl.humanresourcesmanagement.enums.user.EUserRole;
import owl.humanresourcesmanagement.exception.*;
import owl.humanresourcesmanagement.exception.Exception;
import owl.humanresourcesmanagement.mapper.PermissionMapper;
import owl.humanresourcesmanagement.repository.PermissionRepository;
import owl.humanresourcesmanagement.repository.UserRepository;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PermissionService {
	private final TokenService tokenService;
	private final UserRepository userRepository;
	private final PermissionRepository permissionRepository;
	
	public Boolean isApprovedRequestLeave(String token, IsApprovedRequestLeaveRequestDto dto) {
		User userFromToken = tokenService.getToken(token);
		
		Permission permission = permissionRepository.findById(dto.id())
		                                            .orElseThrow(() -> new Exception(ErrorType.PERMISSION_NOT_FOUND));
		
		Optional<User> userFromDto = userRepository.findById(permission.getUserId());
		
		if (userFromDto.isPresent()) {
			User user = userFromDto.get();
			if(!Objects.equals(user.getEmployeeInformation().getCompanyId(), userFromToken.getEmployeeInformation().getCompanyId())) {
				throw new Exception(ErrorType.UNAUTHORIZED_OPERATION);
			}
		} else {
			throw new Exception(ErrorType.USER_NOT_FOUND);
		}
		
		if(permission.getPermissionState() != (EPermissionState.PENDING)) {
			throw new Exception(ErrorType.PERMISSION_STATE_DOESNT_PENDING);
		}
		permission.setPermissionState(dto.isApproved() ? EPermissionState.APPROVED : EPermissionState.REJECTED);
		return dto.isApproved();
	}
	
	public String getLeavesBalance(String token) {
		User employee_user = tokenService.getToken(token);
		
		User user = userRepository.findById(employee_user.getId())
		                       .orElseThrow(() -> new Exception(ErrorType.EMPLOYEE_NOT_FOUND));
		
		return EPermissionPolicy.getAnnualDetail(user.getEmployeeInformation().getDateOfEmployment());
	}
	
	public Boolean createWorkHoliday(String token, @Valid CreateLeaveRequestDto dto) {
		User employee_user = tokenService.getToken(token);
		
		User user = userRepository.findById(employee_user.getId())
		                       .orElseThrow(() -> new Exception(ErrorType.EMPLOYEE_NOT_FOUND));
		
		// Çalışanın daha önce yapılmış ve beklemede olan izin talebi var mı?
		boolean hasPendingRequest = permissionRepository.existsByUserIdAndPermissionState(
				user.getId(), EPermissionState.PENDING);
		
		if (hasPendingRequest) {
			throw new Exception(ErrorType.ALREADY_HAS_PENDING_LEAVE_REQUEST);
		}
		
		// Kalan izin
		int remainingLeave = getAnnualLeaveDetailsDto(user).remainingLeave();
		
		// Talep edilen izin günü sayısı
		int requestedLeave = (int) ChronoUnit.DAYS.between(dto.beginDate(), dto.endDate()) + 1;
		
		// Talep edilen izin, kalan izinden fazla mı?
		if (requestedLeave > remainingLeave) {
			throw new Exception(ErrorType.INSUFFICIENT_LEAVE_BALANCE);
		}
		
		Permission permission = PermissionMapper.INSTANCE.toPermission(dto, employee_user.getId());
		permission.setPermissionState(EPermissionState.PENDING);
		permissionRepository.save(permission);
		return true;
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
	
	public AnnualLeaveDetailsDto getAnnualLeavesDetail(String token) {
		User employee_user = tokenService.getToken(token);
		User user = userRepository.findById(employee_user.getId())
		                       .orElseThrow(() -> new Exception(ErrorType.EMPLOYEE_NOT_FOUND));
		
		return getAnnualLeaveDetailsDto(user);
	}
	
	public List<Permission> getAllMyLeaves(String token) {
		User user = tokenService.getToken(token);
		
		return permissionRepository.findAllByUserIdAndPermissionStateNot(user.getId(), EPermissionState.REJECTED);
	}
	
	public Permission getLeavesDetail(String token, Long permissionId) {
		User user = tokenService.getToken(token);
		
		Permission permission = permissionRepository.findById(permissionId)
				.orElseThrow(() -> new Exception(ErrorType.PERMISSION_NOT_FOUND));
		
		if (!permission.getUserId().equals(user.getId())) {
			throw new Exception(ErrorType.UNAUTHORIZED_OPERATION);
		}
		
		return permission;
	}
	
	public List<Permission> pendingLeave(String token) {
		User userFromToken = tokenService.getToken(token);
		
		if(userFromToken.getRole() != EUserRole.MANAGER) {
			throw new Exception(ErrorType.USER_NOT_MANAGER);
		}
		
		if(userFromToken.getEmployeeInformation().getCompanyId() == null) {
			throw new Exception(ErrorType.COMPANY_OR_EMPLOYEE_NOT_FOUND);
		}
		
		return permissionRepository.findAllByPermissionState(EPermissionState.PENDING, userFromToken.getEmployeeInformation().getCompanyId());
	}
	
	public List<Permission> approvedLeave(String token) {
		User userFromToken = tokenService.getToken(token);
		
		if(userFromToken.getRole() != EUserRole.MANAGER) {
			throw new Exception(ErrorType.USER_NOT_MANAGER);
		}
		
		if(userFromToken.getEmployeeInformation().getCompanyId() == null) {
			throw new Exception(ErrorType.COMPANY_OR_EMPLOYEE_NOT_FOUND);
		}
		
		return permissionRepository.findAllByPermissionState(EPermissionState.APPROVED, userFromToken.getEmployeeInformation().getCompanyId());
	}
	
}
