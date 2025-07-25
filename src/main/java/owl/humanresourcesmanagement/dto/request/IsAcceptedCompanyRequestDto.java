package owl.humanresourcesmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;

public record IsAcceptedCompanyRequestDto(
		@NotBlank(message = "ID cannot empty!")
		Long id,
		
		@NotBlank(message = "IsAccepted cannot empty!")
		Boolean isAccepted
) {

}
