package owl.humanresourcesmanagement.dto.response;

import owl.humanresourcesmanagement.enums.publicHoliday.EPublicHolidays;
import java.time.LocalDate;

public record PublicHolidayResponseDto(
		String name,
		LocalDate startDate,
		LocalDate endDate,
		EPublicHolidays type,
		String description
) {
	public static PublicHolidayResponseDto fromEntity(owl.humanresourcesmanagement.entity.PublicHolidays entity) {
		return new PublicHolidayResponseDto(
				entity.getHolidaysName(),
				entity.getStartDate(),
				entity.getEndDate(),
				entity.getPublicHolidays(),
				entity.getDescription()
		);
	}
}
