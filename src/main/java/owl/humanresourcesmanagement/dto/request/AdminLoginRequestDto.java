package owl.humanresourcesmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AdminLoginRequestDto(
		@NotBlank(message = "Username cannot empty!")
		String username,
		
		@NotBlank(message = "Password cannot empty!")
		String password
) {

}
