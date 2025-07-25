package owl.humanresourcesmanagement.conroller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import owl.humanresourcesmanagement.dto.response.*;
import owl.humanresourcesmanagement.dto.request.*;
import owl.humanresourcesmanagement.service.SpendingService;
import static owl.humanresourcesmanagement.constant.EndPoints.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(COMPANY)
@CrossOrigin("*")
public class SpendingControllerManager {
	private final SpendingService spendingService;
	
	@GetMapping(PENDING_EXPENSES_MANAGER)
	public ResponseEntity<BaseResponse<Page<PersonalSpendingSummaryDto>>> getPendingExpenses(@RequestHeader String token, Pageable pageable) {
		Page<PersonalSpendingSummaryDto> expenses = spendingService.getPendingExpensesForManager(token, pageable);
		
		return ResponseEntity.ok(BaseResponse.<Page<PersonalSpendingSummaryDto>>builder()
		                                     .message("Onay bekleyen harcamalar listelendi.")
		                                     .code(200)
		                                     .success(true)
		                                     .data(expenses)
		                                     .build());
	}
	
	@GetMapping(APPROVED_EXPENSES_MANAGER)
	public ResponseEntity<BaseResponse<Page<PersonalSpendingSummaryDto>>> getApprovedExpenses(@RequestHeader String token, Pageable pageable) {
		Page<PersonalSpendingSummaryDto> expenses = spendingService.getApprovedExpensesForManager(token, pageable);
		
		return ResponseEntity.ok(BaseResponse.<Page<PersonalSpendingSummaryDto>>builder()
		                                     .message("Onaylanmış harcamalar listelendi.")
		                                     .code(200)
		                                     .success(true)
		                                     .data(expenses)
		                                     .build());
	}
	
	@GetMapping(REJECTED_EXPENSES_MANAGER)
	public ResponseEntity<BaseResponse<Page<PersonalSpendingSummaryDto>>> getRejectedExpenses(@RequestHeader String token, Pageable pageable) {
		Page<PersonalSpendingSummaryDto> expenses = spendingService.getRejectedExpensesForManager(token, pageable);
		
		return ResponseEntity.ok(BaseResponse.<Page<PersonalSpendingSummaryDto>>builder()
		                                     .message("Reddedilmiş harcamalar listelendi.")
		                                     .code(200)
		                                     .success(true)
		                                     .data(expenses)
		                                     .build());
	}
	
	@GetMapping(EXPENSES_LIST_MANAGER)
	public ResponseEntity<BaseResponse<Page<PersonalSpendingSummaryDto>>> getAllExpensesForManager(@RequestHeader String token, Pageable pageable) {
		Page<PersonalSpendingSummaryDto> expenses = spendingService.getAllExpensesForManager(token, pageable);
		
		return ResponseEntity.ok(BaseResponse.<Page<PersonalSpendingSummaryDto>>builder()
		                                     .message("Personel harcamaları listelendi (Reddedilenler hariç).")
		                                     .code(200)
		                                     .success(true)
		                                     .data(expenses)
		                                     .build());
	}
	
	@GetMapping(EXPENSE_DETAILS_MANAGER)
	public ResponseEntity<BaseResponse<PersonalSpendingDetailResponseDto>> getExpenseDetailForManager(@RequestHeader String token, @PathVariable Long id) {
		PersonalSpendingDetailResponseDto detail = spendingService.getExpenseDetailForManager(token, id);
		
		return ResponseEntity.ok(BaseResponse.<PersonalSpendingDetailResponseDto>builder()
		                                     .message("Harcama detayı getirildi (Yönetici).")
		                                     .code(200)
		                                     .success(true)
		                                     .data(detail)
		                                     .build());
	}
	
	@PutMapping(APPROVE_REJECT_EXPENSE)
	public ResponseEntity<BaseResponse<Boolean>> approveRejectExpense(@RequestHeader String token, @RequestBody ExpenseApproveRejectRequestDto dto) {
		Boolean result = spendingService.approveRejectExpense(token, dto);
		String message = result ? "Harcama onaylandı!" : "Harcama reddedildi!";
		
		return ResponseEntity.ok(BaseResponse.<Boolean>builder()
		                                     .code(200)
		                                     .data(result)
		                                     .success(true)
		                                     .message(message)
		                                     .build());
	}
	
	@GetMapping(EXPENSES_USER_MONTHLY_SUMMARY)
	public ResponseEntity<BaseResponse<PersonalSpendingSummaryWithTotalResponseDto>> getCompanyUserMonthlySummary(@RequestHeader String token, @PathVariable Long userId, @RequestParam Integer year, @RequestParam Integer month) {
		PersonalSpendingSummaryWithTotalResponseDto result = spendingService.getCompanyUserMonthlySummary(token, userId, year, month);
		
		return ResponseEntity.ok(BaseResponse.<PersonalSpendingSummaryWithTotalResponseDto>builder()
		                                     .message(year + "/" + month + " aylık harcamalar listelendi (Kullanıcı ID: " + userId + ")")
		                                     .code(200)
		                                     .success(true)
		                                     .data(result)
		                                     .build());
	}
}
