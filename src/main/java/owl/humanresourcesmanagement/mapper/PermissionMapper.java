package owl.humanresourcesmanagement.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import owl.humanresourcesmanagement.dto.request.CreateLeaveRequestDto;
import owl.humanresourcesmanagement.entity.Permission;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PermissionMapper  {
	PermissionMapper INSTANCE = Mappers.getMapper(PermissionMapper.class);
	
	@Mapping(target = "userId", source = "userId")
	@Mapping(target = "permissionState", ignore = true) // Serviste PENDING olarak atanacak
	Permission toPermission(CreateLeaveRequestDto dto, Long userId);

}
