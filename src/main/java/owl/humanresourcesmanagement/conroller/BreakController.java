package owl.humanresourcesmanagement.conroller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import owl.humanresourcesmanagement.service.BreakService;
import owl.humanresourcesmanagement.dto.request.*;
import owl.humanresourcesmanagement.dto.response.*;
import java.util.List;
import static owl.humanresourcesmanagement.constant.EndPoints.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BreakController {
	private final BreakService breakService;
	
	@GetMapping(ALL_BREAKS)
	public ResponseEntity<BaseResponse<List<BreakResponseDto>>> getAllBreaks(@RequestHeader String token) {
		List<BreakResponseDto> allBreaks = breakService.getAllBreaks(token);
		return ResponseEntity.ok(BaseResponse.<List<BreakResponseDto>>builder()
		                                     .code(200)
		                                     .data(allBreaks)
		                                     .success(true)
		                                     .message("Breaks retrieved successfully!")
		                                     .build());
	}
	
	@GetMapping(BREAK_DETAILS)
	public ResponseEntity<BaseResponse<BreakDetailsResponseDto>> getBreakDetails(@RequestHeader String token, @PathVariable Long breakId) {
		BreakDetailsResponseDto breakDetails = breakService.getBreakDetailsById(token, breakId);
		return ResponseEntity.ok(BaseResponse.<BreakDetailsResponseDto>builder()
		                                     .code(200)
		                                     .data(breakDetails)
		                                     .success(true)
		                                     .message("Break details retrieved!")
		                                     .build());
	}
	
	@PostMapping(CREATE_BREAK)
	public ResponseEntity<BaseResponse<Boolean>> createBreak(@RequestHeader String token, @RequestBody CreateBreakRequestDto dto) {
		Boolean created = breakService.createBreak(token, dto);
		return ResponseEntity.ok(BaseResponse.<Boolean>builder()
		                                     .code(200)
		                                     .data(created)
		                                     .success(true)
		                                     .message("Break created successfully!")
		                                     .build());
	}
	
	@PutMapping(UPDATE_BREAK)
	public ResponseEntity<BaseResponse<BreakDetailsResponseDto>> updateBreak(@RequestHeader String token, @RequestBody BreakUpdateRequestDto dto, @PathVariable Long breakId) {
		BreakDetailsResponseDto updatedBreak = breakService.updateBreak(token, dto, breakId);
		return ResponseEntity.ok(BaseResponse.<BreakDetailsResponseDto>builder()
		                                     .code(200)
		                                     .data(updatedBreak)
		                                     .success(true)
		                                     .message("Break updated successfully!")
		                                     .build());
	}
	
	@DeleteMapping(DELETE_BREAK)
	public ResponseEntity<BaseResponse<Boolean>> deleteBreak(@RequestHeader String token, @PathVariable Long breakId) {
		Boolean deleted = breakService.deleteBreak(token, breakId);
		return ResponseEntity.ok(BaseResponse.<Boolean>builder()
		                                     .code(200)
		                                     .data(deleted)
		                                     .success(true)
		                                     .message("Break deleted successfully!")
		                                     .build());
	}
	
}
