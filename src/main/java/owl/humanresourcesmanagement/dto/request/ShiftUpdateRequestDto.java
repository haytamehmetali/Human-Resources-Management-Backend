package owl.humanresourcesmanagement.dto.request;

import java.time.LocalTime;

public record ShiftUpdateRequestDto(
		String shiftName,
		Integer capacity,
		LocalTime beginHour,
		LocalTime endHour,
		String description
) {
}
