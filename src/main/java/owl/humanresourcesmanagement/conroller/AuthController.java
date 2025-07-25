package owl.humanresourcesmanagement.conroller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import owl.humanresourcesmanagement.dto.request.*;
import owl.humanresourcesmanagement.dto.response.AdminLoginResponseDto;
import owl.humanresourcesmanagement.dto.response.BaseResponse;
import owl.humanresourcesmanagement.dto.response.ForgotPasswordResponseDto;
import owl.humanresourcesmanagement.dto.response.LoginResponseDto;
import owl.humanresourcesmanagement.exception.*;
import owl.humanresourcesmanagement.exception.Exception;
import owl.humanresourcesmanagement.service.AdminService;
import owl.humanresourcesmanagement.service.AuthService;
import static owl.humanresourcesmanagement.constant.EndPoints.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(AUTHENTICATION)
@CrossOrigin(origins = "*")
public class AuthController {
	private final AuthService authService;
	private final AdminService adminService;
	
	@PostMapping(REGISTER)
	public ResponseEntity<BaseResponse<Boolean>> register(@RequestBody @Valid RegisterRequestDto dto) {
		if (!dto.password().equals((dto.rePassword()))) throw new Exception(ErrorType.PASSWORD_MISMATCH);
		return ResponseEntity.ok(BaseResponse.<Boolean>builder()
		                                     .code(200)
		                                     .data(authService.register(dto))
		                                     .success(true)
		                                     .message("Waiting for e-mail confirmation complete the membership process!")
		                                     .build());
	}
	
	@PutMapping(VERIFY_MAIL)
	public ResponseEntity<String> activateCode(@RequestBody ActivateCodeRequestDto dto) {
		return ResponseEntity.ok(authService.activateCode(dto));
	}
	
	@PostMapping(LOGIN)
	public ResponseEntity<BaseResponse<String>> doLogin(@RequestBody @Valid UserLoginRequestDto dto){
		LoginResponseDto responseDto = authService.login(dto);
		return ResponseEntity.ok(BaseResponse.<String>builder()
		                                     .code(200)
		                                     .data(String.valueOf(responseDto))
		                                     .success(true)
		                                     .message("Login Successful!")
		                                     .build());
	}
	
	@PostMapping(ADMIN_LOGIN)
	public ResponseEntity<BaseResponse<AdminLoginResponseDto>> doLogin(@RequestBody @Valid AdminLoginRequestDto dto) {
		return ResponseEntity.ok(BaseResponse.<AdminLoginResponseDto>builder()
		                                     .code(200)
		                                     .data(adminService.login(dto))
		                                     .success(true)
		                                     .message("Admin Login Successful!")
		                                     .build());
	}
	
	@GetMapping(FORGOT_PASSWORD)
	public ResponseEntity<BaseResponse<ForgotPasswordResponseDto>> forgotPassword(@RequestParam String email) {
		ForgotPasswordResponseDto response = authService.forgotPassword(email);
		return ResponseEntity.ok(BaseResponse.<ForgotPasswordResponseDto>builder()
		                                     .code(200)
		                                     .data(response)
		                                     .success(true)
		                                     .message("Şifre sıfırlama kodu e-posta adresinize gönderildi.")
		                                     .build());
	}
	
	@PostMapping(RESET_PASSWORD)
	public ResponseEntity<BaseResponse<Boolean>> resetPassword(@RequestBody @Valid ResetPasswordRequestDto dto) {
		if (!dto.newPassword().equals((dto.reNewPassword()))) throw new Exception(ErrorType.PASSWORD_MISMATCH);
		boolean isReset = authService.resetPassword(dto);
		return ResponseEntity.ok(BaseResponse.<Boolean>builder()
		                                     .code(200)
		                                     .data(isReset)
		                                     .message("Şifre sıfırlama işlemi başarılı.")
		                                     .success(true)
		                                     .build());
	}
	
	@PostMapping(LOGOUT)
	public ResponseEntity<BaseResponse<Boolean>> logout(@RequestParam String token) {
		authService.logout(token);
		return ResponseEntity.ok(BaseResponse.<Boolean>builder()
		                                     .code(200)
		                                     .data(true)
		                                     .message("Çıkış yapıldı.")
		                                     .success(true)
		                                     .build());
	}
	
	@PostMapping(REFRESH_ACCESS_TOKEN)
	public ResponseEntity<BaseResponse<String>> refreshAccessToken(@RequestParam String refreshToken) {
		String newAccessToken = authService.refreshAccessToken(refreshToken);
		return ResponseEntity.ok(BaseResponse.<String>builder()
		                                     .code(200)
		                                     .success(true)
		                                     .data(newAccessToken)
		                                     .message("Yeni access token üretildi.")
		                                     .build());
	}
	
}
