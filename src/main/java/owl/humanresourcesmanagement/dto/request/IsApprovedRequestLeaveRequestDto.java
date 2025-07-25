package owl.humanresourcesmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;

public record IsApprovedRequestLeaveRequestDto(
		@NotBlank(message = "ID cannot empty!")
		Long id,
		
		@NotBlank(message = "IsApproved cannot empty!")
		Boolean isApproved
) {
}
