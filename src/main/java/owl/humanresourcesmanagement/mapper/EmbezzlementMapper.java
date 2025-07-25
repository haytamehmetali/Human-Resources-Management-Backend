package owl.humanresourcesmanagement.mapper;

import jakarta.validation.Valid;
import org.mapstruct.*;
import org.mapstruct.factory.*;
import owl.humanresourcesmanagement.dto.response.*;
import owl.humanresourcesmanagement.dto.request.*;
import owl.humanresourcesmanagement.entity.Embezzlement;
import owl.humanresourcesmanagement.repository.UserRepository;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmbezzlementMapper {
	EmbezzlementMapper INSTANCE = Mappers.getMapper(EmbezzlementMapper.class);
	
	@Mapping(source = "id", target = "embezzlementId")
	@Mapping(source = "rejectReason", target = "rejectReason")
	EmbezzlementResponseDto toResponseDto(Embezzlement embezzlement);
	
	@Mapping(source = "id", target = "embezzlementId")
	@Mapping(target = "fullName", expression = "java(getFullName(embezzlement.getUserId(), userRepository))")
	@Mapping(source = "rejectReason", target = "rejectReason")
	EmbezzlementProductDetailResponseDto toDetailDto(Embezzlement embezzlement, @Context UserRepository userRepository);
	
	@Mapping(target = "embezzlementId", source = "id")
	@Mapping(target = "fullName", expression = "java(getFullName(embezzlement.getUserId(), userRepository))")
	@Mapping(source = "rejectReason", target = "rejectReason")
	EmbezzlementProductDetailResponseDto toEmbezzlementDetails(Embezzlement embezzlement, @Context UserRepository userRepository);
	
	default String getFullName(Long userId, UserRepository userRepository) {
		if (userId == null) return null;
		
		return userRepository.findById(userId)
		                     .map(user -> user.getEmployeeInformation().getFirstName() + " " + user.getEmployeeInformation().getLastName())
		                     .orElse("Unknown");
	}
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "userId", ignore = true)
	@Mapping(target = "companyId", ignore = true)
	Embezzlement fromCreateDto(CreateEmbezzlementRequestDto dto);
	
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateEmbezzlementFromDto(@Valid EmbezzlementResponseDto requestDto, @MappingTarget Embezzlement embezzlement);
}
