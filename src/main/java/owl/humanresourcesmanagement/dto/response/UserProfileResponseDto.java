package owl.humanresourcesmanagement.dto.response;

import owl.humanresourcesmanagement.enums.user.*;

public record UserProfileResponseDto(
		Long id,
		String firstName,
		String lastName,
		Long companyId,
		String mail,
		String avatar,
		String phone,
		EUserState userState,
		EUserRole role
) {
}
