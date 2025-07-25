package owl.humanresourcesmanagement.dto.request;

import jakarta.validation.constraints.*;
import owl.humanresourcesmanagement.enums.EBreakType;
import java.time.LocalTime;

public record CreateBreakRequestDto(
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
