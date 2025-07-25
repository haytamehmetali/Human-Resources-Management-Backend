package owl.humanresourcesmanagement.dto.response;

import java.time.LocalTime;
import java.util.Set;

public record ShiftResponseDto(
		Long shiftId,
		Set<Long> assignedUserIds,
		String shiftName,
		LocalTime beginHour,
		LocalTime endHour
) {
}
