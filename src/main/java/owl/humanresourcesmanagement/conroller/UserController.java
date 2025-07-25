package owl.humanresourcesmanagement.conroller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import owl.humanresourcesmanagement.dto.request.*;
import owl.humanresourcesmanagement.dto.response.*;
import owl.humanresourcesmanagement.exception.*;
import owl.humanresourcesmanagement.exception.Exception;
import owl.humanresourcesmanagement.service.*;
import static owl.humanresourcesmanagement.constant.EndPoints.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {
	private final UserService userService;
	
	@GetMapping(USER_PROFILE)
	public ResponseEntity<BaseResponse<UserProfileResponseDto>> getProfile(@RequestParam String token) {
		return ResponseEntity.ok(BaseResponse.<UserProfileResponseDto>builder()
		                                     .code(200)
		                                     .data(userService.getProfile(token))
		                                     .success(true)
		                                     .message("Profil bilgileri listelendi.").build());
	}
	
	@PutMapping(USER_UPDATE_PROFILE)
	public ResponseEntity<BaseResponse<UserProfileResponseDto>> updateProfile(String token, @RequestBody @Valid UpdateProfileRequestDto dto) {
		UserProfileResponseDto updated = userService.updateProfile(token, dto);
		return ResponseEntity.ok(BaseResponse.<UserProfileResponseDto>builder()
		                                     .code(200)
		                                     .success(true)
		                                     .message("Profil güncellendi.")
		                                     .data(updated)
		                                     .build());
	}
	
	@PutMapping(USER_CHANGE_EMAIL) //change email valid
	public ResponseEntity<BaseResponse<Boolean>> changeEmail(String token, @RequestBody @Valid ChangeMailRequestDto dto) {
		return ResponseEntity.ok(BaseResponse.<Boolean>builder()
		                                     .code(200)
		                                     .success(true)
		                                     .message("Email değiştime kodu e mail adresinize gönderildi.")
		                                     .data( userService.changeMail(token, dto))
		                                     .build());
	}
	
	@PutMapping(USER_VERIFY_EMAIL)
	public ResponseEntity<BaseResponse<String>> confirmEmailChange(@RequestParam String token, @RequestBody MailVerifyCodeRequestDto dto) {
		userService.confirmEmailChange(token, dto);
		return ResponseEntity.ok(BaseResponse.<String>builder()
		                                     .code(200)
		                                     .success(true)
		                                     .message("Email başarıyla değiştirildi.")
		                                     .data("OK")
		                                     .build());
	}
	
	@PutMapping(USER_CHANGE_PASSWORD)
	public ResponseEntity<BaseResponse<Boolean>> changePassword(@RequestParam String token, @RequestBody @Valid ChangePasswordRequestDto dto) {
		if (!dto.newPassword().equals((dto.reNewPassword()))) throw new Exception(ErrorType.PASSWORD_MISMATCH);
		Boolean isChanged = userService.changePassword(token, dto);
		return ResponseEntity.ok(BaseResponse.<Boolean>builder()
		                                     .code(200)
		                                     .success(isChanged)
		                                     .message("Şifre başarıyla değiştirildi.")
		                                     .data(true)
		                                     .build());
	}
	
	@PutMapping(USER_DEACTIVATE)
	public ResponseEntity<BaseResponse<Boolean>> deactivateAccount(@RequestParam String token, @RequestParam Long userId) {
		userService.deActivateAccount(token, userId);
		return ResponseEntity.ok(BaseResponse.<Boolean>builder()
		                                     .code(200)
		                                     .success(true)
		                                     .message("Kullanıcı askıya alındı.")
		                                     .data(true)
		                                     .build());
	}
	
	// EI
	@GetMapping(EMPLOYEE_DASHBOARD)
	public ResponseEntity<BaseResponse<EmployeeDashboardResponseDto>> getEmployeeDashboard(@RequestHeader String token) {
		EmployeeDashboardResponseDto dashboard = userService.getEmployeeDashboard(token);
		return ResponseEntity.ok(BaseResponse.<EmployeeDashboardResponseDto>builder()
		                                     .code(200)
		                                     .data(dashboard)
		                                     .success(true)
		                                     .message("EmployeeInformation dashboard data retrieved!")
		                                     .build());
	}
	
	@PutMapping(UPDATE_PERSONAL_PROFILE)
	public ResponseEntity<BaseResponse<EmployeeResponseDto>> updateEmployeePersonalDetails(@RequestHeader String token, @RequestBody @Valid EmployeeUpdateProfileRequestDto dto) {
		EmployeeResponseDto updatedEmployee = userService.updateProfile(token, dto);
		return ResponseEntity.ok(BaseResponse.<EmployeeResponseDto>builder()
		                                     .code(200)
		                                     .success(true)
		                                     .message("EmployeeInformation personal details updated successfully.")
		                                     .data(updatedEmployee)
		                                     .build());
	}
}
