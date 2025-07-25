package owl.humanresourcesmanagement.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import owl.humanresourcesmanagement.dto.request.CreatePersonalSpendingRequestDto;
import owl.humanresourcesmanagement.dto.request.UpdatePersonalSpendingRequestDto;
import owl.humanresourcesmanagement.dto.response.PersonalSpendingDetailResponseDto;
import owl.humanresourcesmanagement.dto.response.PersonalSpendingSummaryDto;
import owl.humanresourcesmanagement.entity.PersonalSpending;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PersonalSpendingMapper {
	PersonalSpendingMapper INSTANCE = Mappers.getMapper(PersonalSpendingMapper.class);
	
	PersonalSpendingSummaryDto toPersonalSpendingSummaryDto(PersonalSpending personalSpending);
	
	PersonalSpendingDetailResponseDto toPersonalSpendingDetailResponseDto(PersonalSpending personalSpending);
	
	PersonalSpending toPersonalSpending(CreatePersonalSpendingRequestDto dto);
	
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updatePersonalSpendingFromDto(UpdatePersonalSpendingRequestDto dto, @MappingTarget PersonalSpending entity);
}
