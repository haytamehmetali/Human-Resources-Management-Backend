package owl.humanresourcesmanagement.dto.response;

import java.util.List;

public record EmployeeDashboardResponseDto(
		List<PublicHolidayResponseDto> holidays,
		AnnualLeaveDetailsDto annualLeaveDetails,
		PersonalSpendingSummaryWithTotalResponseDto monthlySpendingSummary,
		List<EmbezzlementProductDetailResponseDto> embezzlementProductDetailResponseDto,
		MyShiftResponseDto myShiftResponseDto
) {
	
	public static EmployeeDashboardResponseDto of(
			List<PublicHolidayResponseDto> holidays,
			AnnualLeaveDetailsDto annualLeaveDetails,
			PersonalSpendingSummaryWithTotalResponseDto monthlySpendingSummary,
			List<EmbezzlementProductDetailResponseDto> embezzlementProductDetailResponseDto,
			MyShiftResponseDto myShiftResponseDto
	) {
		return new EmployeeDashboardResponseDto(
				holidays != null ? holidays : List.of(),
				annualLeaveDetails,
				monthlySpendingSummary,
				embezzlementProductDetailResponseDto,
				myShiftResponseDto
		);
	}
}
