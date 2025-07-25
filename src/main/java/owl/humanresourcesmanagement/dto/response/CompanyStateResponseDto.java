package owl.humanresourcesmanagement.dto.response;

import owl.humanresourcesmanagement.enums.company.ECompanyState;

public record CompanyStateResponseDto(
		Long id,
		String name,
		ECompanyState companyState
) {
}
