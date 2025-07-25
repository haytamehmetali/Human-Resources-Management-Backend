package owl.humanresourcesmanagement.conroller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import owl.humanresourcesmanagement.entity.Embezzlement;
import owl.humanresourcesmanagement.service.EmbezzlementService;
import owl.humanresourcesmanagement.dto.request.*;
import owl.humanresourcesmanagement.dto.response.*;
import java.util.List;
import static owl.humanresourcesmanagement.constant.EndPoints.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EmbezzlementController {
	private final EmbezzlementService embezzlementService;
	
	@PostMapping(CREATE_EMBEZZLEMENT)
	public ResponseEntity<BaseResponse<Boolean>> addEmbezzlement(@RequestParam String token, @Valid @RequestBody CreateEmbezzlementRequestDto dto) {
		Boolean responseDto = embezzlementService.createEmbezzlement(token, dto);
		return ResponseEntity.ok(BaseResponse.<Boolean>builder()
		                                     .code(200)
		                                     .success(true)
		                                     .message("Zimmet şirkete başarıyla eklendi.")
		                                     .data(responseDto)
		                                     .build());
	}
	
	@PostMapping(ASSIGN_EMBEZZLEMENT)
	public ResponseEntity<BaseResponse<Void>> assignEmbezzlementToUser(@RequestParam String token, @RequestBody AssignEmbezzlementRequestDto dto) {
		embezzlementService.assignEmbezzlementToUser(token, dto);
		return ResponseEntity.ok(BaseResponse.<Void>builder()
		                                     .code(200)
		                                     .success(true)
		                                     .message("Zimmet başarıyla çalışana atandı.")
		                                     .build());
	}
	
	@PutMapping(CONFIRM_REJECT)
	public ResponseEntity<BaseResponse<Boolean>> confirmOrRejectEmbezzlement(@RequestParam String token, IsConfirmEmbezzlementRequestDto dto) {
		Boolean result = embezzlementService.confirmOrRejectEmbezzlement(token, dto);
		String message;
		if (result) {
			message = "RequestEmbezzlement Confirmed!";
		} else {
			message = "RequestEmbezzlement Rejected!";
		}
		return ResponseEntity.ok(BaseResponse.<Boolean>builder()
		                                     .code(200)
		                                     .data(result)
		                                     .success(true)
		                                     .message(message)
		                                     .build());
	}
	
	@PutMapping(UPDATE_EMBEZZLEMENT)
	public ResponseEntity<BaseResponse<Void>> updateEmbezzlementProduct(@RequestParam String token,Long embezzlementId , @RequestBody UpdateEmbezzlementProductRequestDto dto) {
		embezzlementService.updateEmbezzlementProduct(token,embezzlementId ,dto);
		return ResponseEntity.ok(BaseResponse.<Void>builder()
		                                     .code(200)
		                                     .success(true)
		                                     .message("Zimmet başarıyla güncellendi.")
		                                     .build());
	}
	
	@DeleteMapping(DELETE_EMBEZZLEMENT)
	public ResponseEntity<BaseResponse<Void>> deleteEmbezzlementProduct(@RequestParam String token, @RequestParam Long productId) {
		embezzlementService.deleteEmbezzlementProduct(token, productId);
		return ResponseEntity.ok(BaseResponse.<Void>builder()
		                                     .code(200)
		                                     .success(true)
		                                     .message("Zimmet başarıyla silindi.")
		                                     .build());
	}
	
	@GetMapping(MY_EMBEZZLEMENT_LIST)
	public ResponseEntity<BaseResponse<List<EmbezzlementProductDetailResponseDto>>> getMyEmbezzlement(@RequestParam String token) {
		List<EmbezzlementProductDetailResponseDto> list = embezzlementService.getMyEmbezzlement(token);
		return ResponseEntity.ok(BaseResponse.<List<EmbezzlementProductDetailResponseDto>>builder()
		                                     .code(200)
		                                     .success(true)
		                                     .message("Zimmetler başarıyla listelendi.")
		                                     .data(list)
		                                     .build());
	}
	
	@GetMapping(ASSIGNED_EMBEZZLEMENT_LIST)
	public ResponseEntity<BaseResponse<List<Embezzlement>>> getAssignedEmbezzlement(@RequestParam String token) {
		return ResponseEntity.ok(BaseResponse.<List<Embezzlement>>builder()
		                                     .code(200)
		                                     .success(true)
		                                     .message("Zimmet listesi getirildi.")
		                                     .data(embezzlementService.getAssignedEmbezzlement(token))
		                                     .build());
	}
	
	@GetMapping(REJECTED_EMBEZZLEMENT_LIST)
	public ResponseEntity<BaseResponse<List<Embezzlement>>> getRejectedEmbezzlement(@RequestParam String token) {
		return ResponseEntity.ok(BaseResponse.<List<Embezzlement>>builder()
		                                     .code(200)
		                                     .success(true)
		                                     .message("Zimmet listesi getirildi.")
		                                     .data(embezzlementService.getRejectedEmbezzlement(token))
		                                     .build());
	}
	
	@GetMapping(UNASSIGNED_EMBEZZLEMENT_LIST)
	public ResponseEntity<BaseResponse<List<Embezzlement>>> getUnAssignedEmbezzlement(@RequestParam String token) {
		return ResponseEntity.ok(BaseResponse.<List<Embezzlement>>builder()
		                                     .code(200)
		                                     .success(true)
		                                     .message("Zimmet listesi getirildi.")
		                                     .data(embezzlementService.getUnAssignedEmbezzlement(token))
		                                     .build());
	}
	
	@GetMapping(EMBEZZLEMENT_LIST)
	public ResponseEntity<BaseResponse<List<EmbezzlementResponseDto>>> getAllEmbezzlementByCompany(@RequestParam String token) {
		List<EmbezzlementResponseDto> allByCompany = embezzlementService.getAllByCompany(token);
		return ResponseEntity.ok(BaseResponse.<List<EmbezzlementResponseDto>>builder()
		                                     .code(200)
		                                     .success(true)
		                                     .message("Şirkete ait tüm zimmetler listelendi.")
		                                     .data(allByCompany)
		                                     .build());
	}
	
	@GetMapping(EMBEZZLEMENT_DETAILS)
	public ResponseEntity<BaseResponse<EmbezzlementProductDetailResponseDto>> getEmbezzlementDetails(@RequestParam String token, @PathVariable Long embezzlementId) {
		EmbezzlementProductDetailResponseDto embezzlementDetails = embezzlementService.getEmbezzlementDetails(token,embezzlementId);
		return ResponseEntity.ok(BaseResponse.<EmbezzlementProductDetailResponseDto>builder()
		                                     .code(200)
		                                     .success(true)
		                                     .message("Şirkete ait tüm zimmetler listelendi.")
		                                     .data(embezzlementDetails)
		                                     .build());
	}
	
}
