package owl.humanresourcesmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import owl.humanresourcesmanagement.enums.company.ECompanyState;

public record ChangeCompanyStatusRequestDto(
		@NotBlank(message = "Company ID cannot empty!")
		Long companyId,
		
		@NotBlank(message = "New status must be one of: PENDING, ACCEPTED, DELETED, DENIED")
		ECompanyState newStatus
) {

}
