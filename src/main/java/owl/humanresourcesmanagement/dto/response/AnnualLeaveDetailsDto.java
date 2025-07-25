package owl.humanresourcesmanagement.dto.response;

public record AnnualLeaveDetailsDto(
		int totalLeave,
		int usedLeave,
		int remainingLeave
) {

}
