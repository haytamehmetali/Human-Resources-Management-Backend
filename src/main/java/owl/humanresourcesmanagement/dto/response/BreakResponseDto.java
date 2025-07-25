package owl.humanresourcesmanagement.dto.response;

import owl.humanresourcesmanagement.enums.EBreakType;
import java.time.*;

public record BreakResponseDto(
		Long breakId,
		Long userId,
		EBreakType breakType,
		LocalTime beginTime,
		LocalTime endTime
) {
}
