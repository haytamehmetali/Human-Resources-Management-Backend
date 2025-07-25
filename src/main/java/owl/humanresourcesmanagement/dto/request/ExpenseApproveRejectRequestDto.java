package owl.humanresourcesmanagement.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ExpenseApproveRejectRequestDto(
		@NotNull(message = "Id boş olamaz")
		@Positive(message = "Id pozitif bir sayı olmalıdır")
		Long id,
		
		@NotNull(message = "isApproved boş olamaz")
		Boolean isApproved

) {

}
