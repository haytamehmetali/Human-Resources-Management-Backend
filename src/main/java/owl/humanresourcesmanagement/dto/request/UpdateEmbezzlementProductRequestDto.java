package owl.humanresourcesmanagement.dto.request;

import owl.humanresourcesmanagement.enums.embezzlement.EEmbezzlementState;
import java.time.LocalDate;

public record UpdateEmbezzlementProductRequestDto(
		String description,
		Long userId,
		LocalDate assignedDate,
		LocalDate returnedDate,
		EEmbezzlementState embezzlementState
) {
}
