package owl.humanresourcesmanagement.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import owl.humanresourcesmanagement.dto.request.*;
import owl.humanresourcesmanagement.dto.response.*;
import owl.humanresourcesmanagement.entity.User;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
	
	User fromRegisterDto(RegisterRequestDto dto);
	
	EmployeeResponseDto toDto(User user);
	
	EmployeeDetailsResponseDto toDetailsDto(User user);
	
	User fromEmployeeRequestDto(EmployeeRequestDto dto);
	
	User fromDto(EmployeeRequestDto dto);
	
	@Mapping(target = "id", ignore = true)
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateEmployeeFromDto(EmployeeUpdateRequestDto dto, @MappingTarget User user);
	
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateEmployeeFromUpdateProfileDto(EmployeeUpdateProfileRequestDto dto, @MappingTarget User user);
	
	EmployeeDashboardResponseDto toEmployeeDashboardResponse(
			List<PublicHolidayResponseDto> publicHolidays,
			AnnualLeaveDetailsDto annualLeaveDetails,
			List<EmbezzlementProductDetailResponseDto> embezzlementList,
			PersonalSpendingSummaryWithTotalResponseDto monthlySpendingSummary,
			MyShiftResponseDto shiftList
	);
	
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateUserFromDto(UpdateProfileRequestDto dto, @MappingTarget User user);
	
	UserProfileResponseDto toUserProfileResponseDTO(User user);
}
