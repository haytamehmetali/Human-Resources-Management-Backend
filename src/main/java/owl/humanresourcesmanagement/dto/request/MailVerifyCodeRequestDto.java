package owl.humanresourcesmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;

public record MailVerifyCodeRequestDto(
		@NotBlank(message = "Verification code cannot empty!")
		String verificationCode
) {

}
