package owl.humanresourcesmanagement.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ChangeMailRequestDto(
		@NotBlank(message = "E-mail cannot empty!")
		@Email(message = "Enter valid e-mail address!")
		String newMail
) {
}
