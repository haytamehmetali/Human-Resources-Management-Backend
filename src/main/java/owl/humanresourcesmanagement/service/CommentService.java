package owl.humanresourcesmanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import owl.humanresourcesmanagement.dto.request.CommentSaveRequestDto;
import owl.humanresourcesmanagement.dto.request.CommentUpdateRequestDto;
import owl.humanresourcesmanagement.dto.response.CommentResponseDto;
import owl.humanresourcesmanagement.entity.*;
import owl.humanresourcesmanagement.enums.company.*;
import owl.humanresourcesmanagement.enums.user.*;
import owl.humanresourcesmanagement.repository.*;
import owl.humanresourcesmanagement.exception.Exception;
import owl.humanresourcesmanagement.exception.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final CommentRepository commentRepository;
	private final TokenService tokenService;
	private final CompanyRepository companyRepository;
	private final UserRepository userRepository;
	private final AdminService adminService;
	
	public void saveComment(CommentSaveRequestDto dto, String token) {
		User manager = tokenService.getToken(token);
		if (!manager.getRole().equals(EUserRole.MANAGER)) {
			throw new Exception(ErrorType.UNAUTHORIZED_OPERATION);
		}
		
		commentRepository.findByCompanyId(manager.getEmployeeInformation().getCompanyId())
		                 .filter(c -> c.getStatus() == ECommentStatus.APPROVED || c.getStatus() == ECommentStatus.PENDING)
		                 .ifPresent(c -> {
			                 throw new Exception(ErrorType.COMMENT_ALREADY_EXITS);
		                 });
		
		companyRepository.findById(manager.getEmployeeInformation().getCompanyId())
		                 .orElseThrow(() -> new Exception(ErrorType.COMPANY_NOT_FOUND));
		
		Comment comment = Comment.builder()
		                         .companyId(manager.getEmployeeInformation().getCompanyId())
		                         .userId(manager.getId())
		                         .position(dto.position())
		                         .content(dto.content())
		                         .status(ECommentStatus.PENDING)
		                         .build();
		
		commentRepository.save(comment);
	}
	
	public void updateComment(CommentUpdateRequestDto dto, String token) {
		User manager = tokenService.getToken(token);
		if (!manager.getRole().equals(EUserRole.MANAGER)) {
			throw new Exception(ErrorType.UNAUTHORIZED_OPERATION);
		}
		
		companyRepository.findById(manager.getEmployeeInformation().getCompanyId())
		                 .orElseThrow(() -> new Exception(ErrorType.COMPANY_NOT_FOUND));
		
		Comment comment = commentRepository.findById(dto.commentId())
		                                   .orElseThrow(() -> new Exception(ErrorType.COMMENT_NOT_FOUND));
		
		if (!comment.getCompanyId().equals(manager.getEmployeeInformation().getCompanyId())) {
			throw new Exception(ErrorType.UNAUTHORIZED_OPERATION);
		}
		
		comment.setPosition(dto.position());
		comment.setContent(dto.content());
		comment.setStatus(ECommentStatus.PENDING);
		commentRepository.save(comment);
	}
	
	public void deleteComment(Long commentId, String token) {
		User manager = tokenService.getToken(token);
		if (!manager.getRole().equals(EUserRole.MANAGER)) {
			throw new Exception(ErrorType.UNAUTHORIZED_OPERATION);
		}
		
		companyRepository.findById(manager.getEmployeeInformation().getCompanyId())
		                 .orElseThrow(() -> new Exception(ErrorType.COMPANY_NOT_FOUND));
		
		Comment comment = commentRepository.findById(commentId)
		                                   .orElseThrow(() -> new Exception(ErrorType.COMMENT_NOT_FOUND));
		
		if (!comment.getCompanyId().equals(manager.getEmployeeInformation().getCompanyId())) {
			throw new Exception(ErrorType.UNAUTHORIZED_OPERATION);
		}
		
		commentRepository.deleteById(commentId);
	}
	
	public List<CommentResponseDto> getAllPublishedComments() {
		return commentRepository.findByStatus(ECommentStatus.APPROVED).stream()
		                        .map(comment -> {
			                        User user = userRepository.findById(comment.getUserId()).orElse(null);
			                        Company company = companyRepository.findById(comment.getCompanyId()).orElse(null);
			                        
			                        return new CommentResponseDto(
					                        comment.getId(),
					                        company != null ? company.getName() : "Bilinmeyen Şirket",
					                        company != null ? company.getLogo() : null,
					                        user != null ? user.getEmployeeInformation().getFirstName() + " " + user.getEmployeeInformation().getLastName() : "Anonim",
					                        comment.getPosition(),
					                        user != null ? user.getAvatar() : null,
					                        comment.getContent().substring(0, Math.min(100, comment.getContent().length()))
			                        );
		                        })
		                        .toList();
	}
	
	public void changeCommentStatus(Long commentId, ECommentStatus status, String token) {
		adminService.getAdminFromToken(token);
		Comment comment = commentRepository.findById(commentId)
		                                   .orElseThrow(() -> new Exception(ErrorType.COMMENT_NOT_FOUND));
		
		// PENDING'e dönüş yasak (isteğe bağlı koruma)
		if (status == ECommentStatus.PENDING) {
			throw new Exception(ErrorType.INVALID_COMMENT_STATUS);
		}
		
		comment.setStatus(status);
		commentRepository.save(comment);
	}
	
	public List<CommentResponseDto> getAllPendingComments(String token) {
		adminService.getAdminFromToken(token);
		return commentRepository.findByStatus(ECommentStatus.PENDING).stream()
		                        .map(comment -> {
			                        User user = userRepository.findById(comment.getUserId()).orElse(null);
			                        Company company = companyRepository.findById(comment.getCompanyId()).orElse(null);
			                        
			                        return new CommentResponseDto(
					                        comment.getId(),
					                        company != null ? company.getName() : "Bilinmeyen Şirket",
					                        company != null ? company.getLogo() : null,
					                        user != null ? user.getEmployeeInformation().getFirstName() + " " + user.getEmployeeInformation().getLastName() : "Anonim",
					                        comment.getPosition(),
					                        user != null ? user.getAvatar() : null,
					                        comment.getContent().substring(0, Math.min(100, comment.getContent().length()))
			                        );
		                        })
		                        .toList();
	}
	
	public List<CommentResponseDto> getAllComments(String token) {
		adminService.getAdminFromToken(token);
		return commentRepository.findAll().stream()
		                        .map(comment -> {
			                        User user = userRepository.findById(comment.getUserId()).orElse(null);
			                        Company company = companyRepository.findById(comment.getCompanyId()).orElse(null);
			                        
			                        return new CommentResponseDto(
					                        comment.getId(),
					                        company != null ? company.getName() : "Bilinmeyen Şirket",
					                        company != null ? company.getLogo() : null,
					                        user != null ? user.getEmployeeInformation().getFirstName() + " " + user.getEmployeeInformation().getLastName() : "Anonim",
					                        comment.getPosition(),
					                        user != null ? user.getAvatar() : null,
					                        comment.getContent().substring(0, Math.min(100, comment.getContent().length()))
			                        );
		                        })
		                        .toList();
	}
	
}
