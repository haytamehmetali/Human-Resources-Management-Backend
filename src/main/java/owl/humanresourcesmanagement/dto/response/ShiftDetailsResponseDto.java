package owl.humanresourcesmanagement.dto.response;

import java.time.LocalTime;
import java.util.Set;

public record ShiftDetailsResponseDto(
		Long companyId,
		Long shiftId,
		Integer capacity,
		String shiftName,
		LocalTime beginHour,
		LocalTime endHour,
		String description,
		Set<Long> assignedUserIds,
		Set<String> fullNames

) {
}
