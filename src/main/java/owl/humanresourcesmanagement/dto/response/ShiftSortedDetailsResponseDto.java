package owl.humanresourcesmanagement.dto.response;

import java.time.LocalTime;
import java.util.List;

public record ShiftSortedDetailsResponseDto(
		Long companyId,
		Long shiftId,
		Integer capacity,
		String shiftName,
		LocalTime beginHour,
		LocalTime endHour,
		String description,
		List<Long> assignedUserIds,
		List<String> fullNames
) {
}
