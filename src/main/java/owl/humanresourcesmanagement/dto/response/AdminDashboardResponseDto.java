package owl.humanresourcesmanagement.dto.response;

import lombok.Builder;

@Builder
public record AdminDashboardResponseDto(
		String title,
		int totalCompanies,
		int totalActiveCompanies,
		int totalPersonal,
		int totalActivePersonal

) {
	// Record içine static factory method ekliyoruz
	public static AdminDashboardResponseDto of(
			String title,
			Long totalCompanies,
			Long totalActiveCompanies,
			Long totalPersonal,
			Long totalActivePersonal
	
	) {
		return new AdminDashboardResponseDto(
				title,
				totalCompanies != null ? totalCompanies.intValue() : 0,
				totalActiveCompanies != null ? totalActiveCompanies.intValue() : 0,
				totalPersonal != null ? totalPersonal.intValue() : 0,
				totalActivePersonal != null ? totalActivePersonal.intValue() : 0
		
		);
	}
}
