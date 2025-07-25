package owl.humanresourcesmanagement.conroller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import owl.humanresourcesmanagement.dto.response.*;
import owl.humanresourcesmanagement.dto.request.*;
import owl.humanresourcesmanagement.service.ShiftService;
import java.util.List;
import static owl.humanresourcesmanagement.constant.EndPoints.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ShiftController {
	private final ShiftService shiftService;
	
	@PostMapping(CREATE_SHIFT)
	public ResponseEntity<BaseResponse<Boolean>> createShift(@RequestHeader String token, @RequestBody @Valid CreateShiftRequestDto dto) {
		Boolean createShift = shiftService.createShift(token, dto);
		return ResponseEntity.ok(BaseResponse.<Boolean>builder()
		                                     .code(200)
		                                     .data(createShift)
		                                     .success(true)
		                                     .message("New shift created!")
		                                     .build());
	}
	
	@GetMapping(ALL_SHIFTS)
	public ResponseEntity<BaseResponse<List<ShiftResponseDto>>> getAllShift(@RequestHeader String token) {
		List<ShiftResponseDto> allShift = shiftService.getAllShift(token);
		return ResponseEntity.ok(BaseResponse.<List<ShiftResponseDto>>builder()
		                                     .code(200)
		                                     .data(allShift)
		                                     .success(true)
		                                     .message("Shift retrieved successfully!")
		                                     .build());
	}
	
	@GetMapping(SHIFT_DETAILS)
	public ResponseEntity<BaseResponse<ShiftSortedDetailsResponseDto>> getShiftDetailsById(@RequestParam String token, @PathVariable Long shiftId) {
		ShiftSortedDetailsResponseDto shiftDetails = shiftService.getShiftDetailsById(token, shiftId);
		return ResponseEntity.ok(BaseResponse.<ShiftSortedDetailsResponseDto>builder()
		                                     .code(200)
		                                     .data(shiftDetails)
		                                     .success(true)
		                                     .message("Shift details retrieved!")
		                                     .build());
	}
	
	@PutMapping(UPDATE_SHIFT)
	public ResponseEntity<BaseResponse<ShiftDetailsResponseDto>> updateShift(@RequestParam String token, @RequestBody @Valid ShiftUpdateRequestDto dto, @PathVariable Long shiftId) {
		ShiftDetailsResponseDto updateShift = shiftService.updateShift(token, dto, shiftId);
		return ResponseEntity.ok(BaseResponse.<ShiftDetailsResponseDto>builder()
		                                     .code(200)
		                                     .data(updateShift)
		                                     .success(true)
		                                     .message("Shift updated successfully!")
		                                     .build());
	}
	
	@DeleteMapping(DELETE_SHIFT)
	public ResponseEntity<BaseResponse<Boolean>> deleteShift(@RequestParam String token, @PathVariable Long shiftId) {
		Boolean deleteShift = shiftService.deleteShift(token, shiftId);
		return ResponseEntity.ok(BaseResponse.<Boolean>builder()
		                                     .code(200)
		                                     .data(deleteShift)
		                                     .success(true)
		                                     .message("Shift deleted successfully!")
		                                     .build());
	}
	
	@PostMapping(ASSIGN_SHIFT)
	public ResponseEntity<BaseResponse<Boolean>> assignShift(@RequestParam String token, @PathVariable Long userId, @RequestBody @Valid AssignShiftRequestDto dto) {
		Boolean assignShift = shiftService.assignShift(token, userId, dto);
		return ResponseEntity.ok(BaseResponse.<Boolean>builder()
		                                     .code(200)
		                                     .data(assignShift)
		                                     .success(true)
		                                     .message("Shift assigned successfully!")
		                                     .build());
	}
	
	@DeleteMapping(ASSIGN_DELETE_SHIFT)
	public ResponseEntity<BaseResponse<Boolean>> assignDeleteShift(@RequestParam String token, @PathVariable Long userId, @PathVariable Long shiftId) {
		Boolean assignDeleteShift = shiftService.assignDeleteShift(token, userId, shiftId);
		return ResponseEntity.ok(BaseResponse.<Boolean>builder()
		                                     .code(200)
		                                     .data(assignDeleteShift)
		                                     .success(true)
		                                     .message("ShiftAssigned deleted successfully!")
		                                     .build());
	}
	
	@GetMapping(PERSONAL_SHIFT)
	public ResponseEntity<BaseResponse<PersonalShiftDetailsResponseDto>> getPersonalShift(@RequestParam String token, @PathVariable Long userId) {
		PersonalShiftDetailsResponseDto getPersonalShift = shiftService.getPersonalShift(token, userId);
		return ResponseEntity.ok(BaseResponse.<PersonalShiftDetailsResponseDto>builder()
		                                     .code(200)
		                                     .data(getPersonalShift)
		                                     .success(true)
		                                     .message("Personal shift retrieved successfully!")
		                                     .build());
	}
	
	@GetMapping(MY_SHIFT)
	public ResponseEntity<BaseResponse<MyShiftResponseDto>> getMyShift(@RequestHeader String token) {
		MyShiftResponseDto myShift = shiftService.getMyShift(token);
		return ResponseEntity.ok(BaseResponse.<MyShiftResponseDto>builder()
		                                     .code(200)
		                                     .data(myShift)
		                                     .success(true)
		                                     .message("My shift retrieved successfully!")
		                                     .build());
	}
	
	@GetMapping(ALL_ASSIGNED_SHIFTS)
	public ResponseEntity<BaseResponse<List<ShiftResponseDto>>> getAllAssignedShifts(@RequestHeader String token) {
		List<ShiftResponseDto> getAllAssignedShifts = shiftService.getAllAssignedShifts(token);
		return ResponseEntity.ok(BaseResponse.<List<ShiftResponseDto>>builder()
		                                     .code(200)
		                                     .data(getAllAssignedShifts)
		                                     .success(true)
		                                     .message("Assigned shifts retrieved successfully!")
		                                     .build());
	}
	
}
