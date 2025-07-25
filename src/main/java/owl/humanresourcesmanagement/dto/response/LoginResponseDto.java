package owl.humanresourcesmanagement.dto.response;

import owl.humanresourcesmanagement.enums.user.EUserRole;

public record LoginResponseDto(
		String accessToken,
		String refreshToken,
		EUserRole role
) {

}
