package owl.humanresourcesmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ActivateCodeRequestDto(
		@NotBlank(message = "ID cannot empty!")
		Long id,
		
		@NotBlank(message = "Activation code cannot empty!")
		String activationCode
) {

}
