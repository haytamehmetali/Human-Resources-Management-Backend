package owl.humanresourcesmanagement.dto.request;

import jakarta.validation.constraints.NotNull;

public record AssignShiftRequestDto(
		@NotNull(message = "Shift Id cannot empty!")
		Long shiftId
) {
}
