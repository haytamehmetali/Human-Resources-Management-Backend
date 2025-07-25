package owl.humanresourcesmanagement.dto.response;

public record ForgotPasswordResponseDto(
		String token,
		String email,
		String infoMessage
) {
}
