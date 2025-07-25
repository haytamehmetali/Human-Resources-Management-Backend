package owl.humanresourcesmanagement.dto.response;

import owl.humanresourcesmanagement.enums.EAdminRole;

public record AdminLoginResponseDto(
		String accessToken,
		String refreshToken,
		EAdminRole role
) {

}
