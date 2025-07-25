package owl.humanresourcesmanagement.dto.response;

import java.util.List;

public record CompanyDashboardResponseDto(
		String title,
		int totalEmployees,
		int countApprovedPermissionsToday,
		List<PublicHolidayResponseDto> holidays
        //List<String> announcement
) {
	
	public static CompanyDashboardResponseDto of(
			String title,
			Long totalEmployees,
			Long countApprovedPermissionsToday,
			List<PublicHolidayResponseDto> holidays
            //List<String> announcement
	){
		return new CompanyDashboardResponseDto(
				title,
				totalEmployees != null ? totalEmployees.intValue() : 0,
				countApprovedPermissionsToday != null ? countApprovedPermissionsToday.intValue() : 0,
				holidays != null ? holidays : List.of()
                //announcement != null ? announcement : List.of()
		);
	}
}
