package owl.humanresourcesmanagement.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import owl.humanresourcesmanagement.dto.response.CompanyStateResponseDto;
import owl.humanresourcesmanagement.entity.Company;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompanyMapper {
	CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);
	
	List<CompanyStateResponseDto> toStateDtoList(List<Company> companies);
}
