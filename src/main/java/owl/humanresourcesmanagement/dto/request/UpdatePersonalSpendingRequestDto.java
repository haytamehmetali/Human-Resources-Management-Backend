package owl.humanresourcesmanagement.dto.request;

import owl.humanresourcesmanagement.enums.spendings.ESpendingType;
import java.time.LocalDate;

public record UpdatePersonalSpendingRequestDto(
		String description,
		Double billAmount,
		LocalDate spendingDate,
		ESpendingType spendingType,
		String billDocumentUrl
) {
}
