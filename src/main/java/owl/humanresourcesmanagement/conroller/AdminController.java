package owl.humanresourcesmanagement.conroller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import owl.humanresourcesmanagement.dto.request.ChangeCompanyStatusRequestDto;
import owl.humanresourcesmanagement.dto.request.IsAcceptedCompanyRequestDto;
import owl.humanresourcesmanagement.dto.response.AdminDashboardResponseDto;
import owl.humanresourcesmanagement.dto.response.BaseResponse;
import owl.humanresourcesmanagement.dto.response.CompanyStateResponseDto;
import owl.humanresourcesmanagement.service.AdminService;
import java.util.List;
import static owl.humanresourcesmanagement.constant.EndPoints.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ADMIN)
@CrossOrigin(origins = "*")
public class AdminController {
	private final AdminService adminService;
	
	@GetMapping(ADMIN_DASHBOARD)
	public ResponseEntity<BaseResponse<AdminDashboardResponseDto>> getAdminDashboard(@RequestHeader String token) {
		AdminDashboardResponseDto dashboard = adminService.getAdminDashboard(token);
		return ResponseEntity.ok(BaseResponse.<AdminDashboardResponseDto>builder()
		                                     .code(200)
		                                     .data(dashboard)
		                                     .success(true)
		                                     .message("Admin dashboard data retrieved!")
		                                     .build());
	}
	
	@GetMapping(PENDING_COMPANY)
	public ResponseEntity<BaseResponse<List<CompanyStateResponseDto>>> pendingCompanies(@RequestParam String token) {
		List<CompanyStateResponseDto> pendingCompanies = adminService.listAllPendingCompanies(token);
		return ResponseEntity.ok(BaseResponse.<List<CompanyStateResponseDto>>builder()
		                                     .code(200)
		                                     .data(pendingCompanies)
		                                     .success(true)
		                                     .message("Pending Companies!")
		                                     .build());
	}
	
	@GetMapping(ACCEPTED_COMPANY)
	public ResponseEntity<BaseResponse<List<CompanyStateResponseDto>>> acceptedCompanies(@RequestParam String token) {
		List<CompanyStateResponseDto> acceptedCompanies = adminService.listAllAcceptedCompanies(token);
		return ResponseEntity.ok(BaseResponse.<List<CompanyStateResponseDto>>builder()
		                                     .code(200)
		                                     .data(acceptedCompanies)
		                                     .success(true)
		                                     .message("Accepted Companies!")
		                                     .build());
	}
	
	@GetMapping(DENIED_COMPANY)
	public ResponseEntity<BaseResponse<List<CompanyStateResponseDto>>> deniedCompanies(@RequestParam String token) {
		List<CompanyStateResponseDto> deniedCompanies = adminService.listAllDeniedCompanies(token);
		return ResponseEntity.ok(BaseResponse.<List<CompanyStateResponseDto>>builder()
		                                     .code(200)
		                                     .data(deniedCompanies)
		                                     .success(true)
		                                     .message("Denied Companies!")
		                                     .build());
	}
	
	@GetMapping(DELETED_COMPANY)
	public ResponseEntity<BaseResponse< List<CompanyStateResponseDto>>> deletedCompanies(@RequestParam String token) {
		List<CompanyStateResponseDto> deletedCompanies = adminService.listAllDeletedCompanies(token);
		return ResponseEntity.ok(BaseResponse.< List<CompanyStateResponseDto>>builder()
		                                     .code(200)
		                                     .data(deletedCompanies)
		                                     .success(true)
		                                     .message("Deleted Companies!")
		                                     .build());
	}
	
	@GetMapping(FIND_ALL_COMPANY)
	public ResponseEntity<BaseResponse< List<CompanyStateResponseDto>>> findAllCompanies(@RequestParam String token) {
		List<CompanyStateResponseDto> findAllCompanies = adminService.listAllFindAllCompanies(token);
		return ResponseEntity.ok(BaseResponse.< List<CompanyStateResponseDto>>builder()
		                                     .code(200)
		                                     .data(findAllCompanies)
		                                     .success(true)
		                                     .message("Find All Companies!")
		                                     .build());
	}
	
	@PutMapping(CHANGE_COMPANY_STATUS)
	public ResponseEntity<BaseResponse<Boolean>> changeCompanyStatus(@RequestParam String token, ChangeCompanyStatusRequestDto dto) {
		return ResponseEntity.ok(BaseResponse.<Boolean>builder()
		                                     .code(200)
		                                     .data(adminService.changeCompanyStatus(token, dto))
		                                     .success(true)
		                                     .message("Change Company Status! - NewCompanyState")
		                                     .build());
	}
	
	@PutMapping(IS_ACCEPTED_COMPANY)
	public ResponseEntity<BaseResponse<Boolean>> isAcceptedCompany (@RequestParam String token, IsAcceptedCompanyRequestDto dto) {
		Boolean result = adminService.IsAcceptedCompany(token, dto);
		String message;
		if (result) {
			message = "Company Accepted!";
		} else {
			message = "Company Denied!";
		}
		
		return ResponseEntity.ok(BaseResponse.<Boolean>builder()
		                                     .code(200)
		                                     .data(result)
		                                     .success(true)
		                                     .message(message)
		                                     .build());
	}
	
	@PostMapping(ADMIN_DEACTIVATE)
	public ResponseEntity<BaseResponse<Boolean>> deactivateAccount(@RequestParam String token) {
		adminService.deActivateAccount(token);
		return ResponseEntity.ok(BaseResponse.<Boolean>builder()
		                                     .code(200)
		                                     .success(true)
		                                     .message("Hesap askıya alındı.")
		                                     .data(true)
		                                     .build());
	}
	
	@PostMapping(REFRESH_ACCESS_TOKEN)
	public ResponseEntity<BaseResponse<String>> refreshAccessToken(@RequestParam String refreshToken) {
		String newAccessToken = adminService.refreshAccessToken(refreshToken);
		return ResponseEntity.ok(BaseResponse.<String>builder()
		                                     .code(200)
		                                     .success(true)
		                                     .data(newAccessToken)
		                                     .message("Yeni access token üretildi.")
		                                     .build());
	}
	
	@PostMapping(LOGOUT)
	public ResponseEntity<BaseResponse<Boolean>> logout(@RequestParam String token) {
		adminService.logout(token);
		return ResponseEntity.ok(BaseResponse.<Boolean>builder()
		                                     .code(200)
		                                     .data(true)
		                                     .message("Çıkış yapıldı..")
		                                     .success(true)
		                                     .build());
	}
	
}
