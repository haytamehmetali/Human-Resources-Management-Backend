package owl.humanresourcesmanagement.dto.response;

import java.util.List;

public record PersonalSpendingSummaryWithTotalResponseDto(
		List<PersonalSpendingSummaryDto> expenses,
		Double totalAmount
) {}
