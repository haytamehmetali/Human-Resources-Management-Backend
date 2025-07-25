package owl.humanresourcesmanagement.dto.request;

import jakarta.validation.constraints.*;
import owl.humanresourcesmanagement.enums.EBreakType;
import java.time.*;

public record BreakUpdateRequestDto(
		@NotNull
		EBreakType breakType,
		
		@NotNull
		LocalTime beginTime,
		
		@NotNull
		LocalTime endTime,
		
		String description,
		
		@NotNull
		Long userId
) {
}
