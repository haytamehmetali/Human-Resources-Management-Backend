package owl.humanresourcesmanagement.dto.response;

import owl.humanresourcesmanagement.enums.embezzlement.*;
import java.time.LocalDate;

public record EmbezzlementProductDetailResponseDto(
		Long userId,
		String fullName,
		Long embezzlementId,
		String name,
		String brand,
		String model,
		String serialNumber,
		EEmbezzlementType embezzlementType,
		EEmbezzlementState embezzlementState,
		LocalDate assignedDate,
		LocalDate returnedDate,
		String rejectReason
) {
}
