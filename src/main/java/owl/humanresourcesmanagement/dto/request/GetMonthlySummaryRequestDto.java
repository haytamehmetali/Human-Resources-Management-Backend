package owl.humanresourcesmanagement.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record GetMonthlySummaryRequestDto(
		@NotNull(message = "Yıl alanı zorunludur.")
		Integer year,
		
		@NotNull(message = "Ay alanı zorunludur.")
		@Min(value = 1, message = "Ay 1-12 arasında olmalıdır.")
		@Max(value = 12, message = "Ay 1-12 arasında olmalıdır.")
		Integer month
) {
}
