package owl.humanresourcesmanagement.conroller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import owl.humanresourcesmanagement.dto.request.*;
import owl.humanresourcesmanagement.dto.response.*;
import owl.humanresourcesmanagement.enums.company.ECommentStatus;
import owl.humanresourcesmanagement.service.CommentService;
import java.util.List;
import static owl.humanresourcesmanagement.constant.EndPoints.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CommentController {
	private final CommentService commentService;
	
	@GetMapping(PUBLIC_COMMENTS)
	public ResponseEntity<BaseResponse<List<CommentResponseDto>>> getAllPublishedComments() {
		List<CommentResponseDto> comments = commentService.getAllPublishedComments();
		return ResponseEntity.ok(BaseResponse.<List<CommentResponseDto>>builder()
		                                     .code(200)
		                                     .success(true)
		                                     .message("Yayınlanmış yorumlar listelendi.")
		                                     .data(comments)
		                                     .build());
	}
	
	@DeleteMapping(DELETE_COMMENT)
	public ResponseEntity<BaseResponse<Void>> deleteComment(@PathVariable Long id, @RequestHeader String token) {
		commentService.deleteComment(id, token);
		return ResponseEntity.ok(BaseResponse.<Void>builder()
		                                     .code(200)
		                                     .success(true)
		                                     .message("Yorum başarıyla silindi.")
		                                     .build());
	}
	
	@GetMapping( ADMIN_ALL_COMMENTS)
	public ResponseEntity<BaseResponse<List<CommentResponseDto>>> getAllComments(@RequestHeader String token) {
		List<CommentResponseDto> comments = commentService.getAllComments(token);
		return ResponseEntity.ok(BaseResponse.<List<CommentResponseDto>>builder()
		                                     .code(200)
		                                     .success(true)
		                                     .message("Tüm yorumlar listelendi.")
		                                     .data(comments)
		                                     .build());
	}
	
	@GetMapping(ADMIN_PENDING_COMMENTS)
	public ResponseEntity<BaseResponse<List<CommentResponseDto>>> getAllPendingComments(@RequestHeader String token) {
		List<CommentResponseDto> comments = commentService.getAllPendingComments(token);
		return ResponseEntity.ok(BaseResponse.<List<CommentResponseDto>>builder()
		                                     .code(200)
		                                     .success(true)
		                                     .message("Bekleyen yorumlar listelendi.")
		                                     .data(comments)
		                                     .build());
	}
	
	@PutMapping( UPDATE_COMMENT)
	public ResponseEntity<BaseResponse<Void>> updateComment(@RequestBody CommentUpdateRequestDto dto, @RequestHeader String token) {
		commentService.updateComment(dto, token);
		return ResponseEntity.ok(BaseResponse.<Void>builder()
		                                     .code(200)
		                                     .success(true)
		                                     .message("Yorum başarıyla güncellendi.")
		                                     .build());
	}
	
	@PostMapping(SAVE_COMMENT)
	public ResponseEntity<BaseResponse<Void>> saveComment(@RequestBody CommentSaveRequestDto dto, @RequestHeader String token) {
		commentService.saveComment(dto, token);
		return ResponseEntity.ok(BaseResponse.<Void>builder()
		                                     .code(200)
		                                     .success(true)
		                                     .message("Yorum başarıyla kaydedildi.")
		                                     .build());
	}
	
	@PutMapping(COMMENT_APPROVE_OR_REJECT)
	public ResponseEntity<BaseResponse<Void>> approveOrRejectComment(@PathVariable Long id, @RequestBody CommentApproveOrRejectRequestDto dto, @RequestHeader String token) {
		if (dto.status() != ECommentStatus.APPROVED && dto.status() != ECommentStatus.REJECTED) {
			return ResponseEntity.badRequest().body(BaseResponse.<Void>builder()
			                                                    .code(400)
			                                                    .success(false)
			                                                    .message("Yalnızca APPROVED veya REJECTED durumu gönderilebilir.")
			                                                    .build());
		}
		
		commentService.changeCommentStatus(id, dto.status(), token);
		return ResponseEntity.ok(BaseResponse.<Void>builder()
		                                     .code(200)
		                                     .success(true)
		                                     .message("Yorum durumu başarıyla güncellendi.")
		                                     .build());
	}
	
}
