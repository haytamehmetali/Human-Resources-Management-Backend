package owl.humanresourcesmanagement.dto.request;

public record CommentUpdateRequestDto(
		Long commentId,
		String position,
		String content
) {
}
