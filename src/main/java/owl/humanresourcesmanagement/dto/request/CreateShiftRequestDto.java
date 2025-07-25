package owl.humanresourcesmanagement.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record CreateShiftRequestDto(
		@NotNull(message = "Capacity cannot empty!")
		Integer capacity,
		
		@NotNull(message = "Shift name cannot empty!")
		String shiftName,
		
		@NotNull(message = "Begin hour cannot empty!")
		LocalTime beginHour,
		
		@NotNull(message = "End hour cannot empty!")
		LocalTime endHour,
		
		@NotNull(message = "Description cannot empty!")
		String description
) {
}
