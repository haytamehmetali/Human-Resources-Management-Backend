package owl.humanresourcesmanagement.dto.request;

import owl.humanresourcesmanagement.enums.embezzlement.EEmbezzlementType;

public record CreateEmbezzlementRequestDto(
		String name,
		String brand,
		String model,
		String serialNumber,
		String description,
		EEmbezzlementType embezzlementType
) {
}
