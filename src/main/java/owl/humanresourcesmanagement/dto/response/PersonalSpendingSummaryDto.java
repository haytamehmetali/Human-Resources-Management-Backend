package owl.humanresourcesmanagement.dto.response;

import java.time.LocalDate;

public record PersonalSpendingSummaryDto(
		Long id,
		LocalDate spendingDate,
		Double billAmount
) {
}
