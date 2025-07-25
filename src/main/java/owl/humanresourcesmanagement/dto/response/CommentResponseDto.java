package owl.humanresourcesmanagement.dto.response;

public record CommentResponseDto(
		Long id,
		String companyName,
		String companyLogo,
		String userFullName,
		String position,
		String userAvatar,
		String content
) {
}
