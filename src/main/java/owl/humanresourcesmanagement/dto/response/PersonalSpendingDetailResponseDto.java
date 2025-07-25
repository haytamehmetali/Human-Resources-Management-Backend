package owl.humanresourcesmanagement.dto.response;

import java.time.LocalDate;

public record PersonalSpendingDetailResponseDto(
		LocalDate spendingDate,
		String description,
		Double billAmount,
		String spendingType,
		String spendingState,
		String documentUrl
) {

}
