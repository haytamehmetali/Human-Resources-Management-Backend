package owl.humanresourcesmanagement.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import owl.humanresourcesmanagement.dto.request.CreateShiftRequestDto;
import owl.humanresourcesmanagement.dto.request.ShiftUpdateRequestDto;
import owl.humanresourcesmanagement.dto.response.*;
import owl.humanresourcesmanagement.entity.Shift;
import owl.humanresourcesmanagement.entity.User;
import owl.humanresourcesmanagement.repository.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShiftMapper {
	ShiftMapper INSTANCE = Mappers.getMapper(ShiftMapper.class);
	
	ShiftResponseDto toDto(Shift shift);
	
	@Mapping(target = "fullNames", expression = "java(getFullNames(shift.getAssignedUserIds(), userRepository))")
	ShiftDetailsResponseDto toDetailsDto(Shift shift, @Context UserRepository userRepository);
	
	default Set<String> getFullNames(Set<Long> userIds, UserRepository userRepository) {
		if (userIds == null || userIds.isEmpty()) return Set.of();
		
		return userIds.stream()
		              .map(userId -> userRepository.findById(userId)
		                                        .map(user -> user.getEmployeeInformation().getFirstName() + " " + user.getEmployeeInformation().getLastName())
		                                        .orElse("Unknown"))
		              .collect(Collectors.toSet());
	}
	
	MyShiftResponseDto toMyShiftDto(Shift shift);
	
	default PersonalShiftDetailsResponseDto toPersonalShiftDetailsDto(Shift shift, User user) {
		if (shift == null || user == null) return null;
		
		return new PersonalShiftDetailsResponseDto(
				user.getEmployeeInformation().getFirstName() + " " + user.getEmployeeInformation().getLastName(),
				shift.getShiftId(),
				shift.getShiftName(),
				shift.getBeginHour(),
				shift.getEndHour(),
				shift.getDescription()
		);
	}
	
	@Mapping(target = "assignedUserIds", expression = "java(getSortedUserIds(shift.getAssignedUserIds()))")
	@Mapping(target = "fullNames", expression = "java(getSortedFullNames(shift.getAssignedUserIds(), userRepository))")
	ShiftSortedDetailsResponseDto toSortedDetailsDto(Shift shift, @Context UserRepository userRepository);
	
	default List<Long> getSortedUserIds(Set<Long> userIds) {
		if (userIds == null || userIds.isEmpty()) return List.of();
		return userIds.stream().sorted().toList();
	}
	
	default List<String> getSortedFullNames(Set<Long> userIds, UserRepository userRepository) {
		if (userIds == null || userIds.isEmpty()) return List.of();
		
		return userIds.stream()
		              .sorted()
		              .map(userId -> userRepository.findById(userId)
		                                        .map(user -> user.getEmployeeInformation().getFirstName() + " " + user.getEmployeeInformation().getLastName())
		                                        .orElse("Unknown"))
		              .toList();
	}
	
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateShiftFromDto(ShiftUpdateRequestDto dto, @MappingTarget Shift shift);
	
	@Mapping(target = "shiftId", ignore = true)
	@Mapping(target = "companyId", ignore = true)
	@Mapping(target = "assignedUserIds", ignore = true)
	Shift fromCreateDto(CreateShiftRequestDto dto);
	
}
