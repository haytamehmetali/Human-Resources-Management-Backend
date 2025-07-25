package owl.humanresourcesmanagement.dto.response;

import java.time.LocalTime;

public record MyShiftResponseDto(
		Long shiftId,
		String shiftName,
		LocalTime beginHour,
		LocalTime endHour

) {
}
