package owl.humanresourcesmanagement.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserLoginRequestDto(
		@NotBlank(message = "E-mail cannot empty!")
		@Email(message = "Enter valid e-mail address!")
		String mail,
		
		@NotBlank(message = "Password cannot empty!")
		String password
) {

}
