package owl.humanresourcesmanagement.dto.response;

import java.time.LocalTime;

public record PersonalShiftDetailsResponseDto(
		String fullName,
		Long shiftId,
		String shiftName,
		LocalTime beginHour,
		LocalTime endHour,
		String description
) {
}
