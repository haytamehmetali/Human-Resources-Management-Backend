package owl.humanresourcesmanagement.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import owl.humanresourcesmanagement.dto.request.BreakUpdateRequestDto;
import owl.humanresourcesmanagement.dto.request.CreateBreakRequestDto;
import owl.humanresourcesmanagement.dto.response.BreakDetailsResponseDto;
import owl.humanresourcesmanagement.dto.response.BreakResponseDto;
import owl.humanresourcesmanagement.entity.Break;
import owl.humanresourcesmanagement.repository.*;
import owl.humanresourcesmanagement.service.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BreakMapper {
	BreakMapper INSTANCE = Mappers.getMapper(BreakMapper.class);
	
	BreakResponseDto toDto(Break aBreak);
	
	@Mapping(target = "fullName", expression = "java(getFullName(aBreak.getUserId(), userRepository))")
	BreakDetailsResponseDto toDetailsDto(Break aBreak, @Context UserRepository userRepository);
	
	default String getFullName(Long userId, UserRepository userRepository) {
		if (userId == null) return null;
		
		return userRepository.findById(userId)
		                  .map(user -> user.getEmployeeInformation().getFirstName() + " " + user.getEmployeeInformation().getLastName())
		                  .orElse("Unknown");
	}
	
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateBreakFromDto(BreakUpdateRequestDto dto, @MappingTarget Break aBreak);
	
	@Mapping(target = "breakId", ignore = true)
	@Mapping(target = "companyId", ignore = true)
	Break fromCreateDto(CreateBreakRequestDto dto);
}
