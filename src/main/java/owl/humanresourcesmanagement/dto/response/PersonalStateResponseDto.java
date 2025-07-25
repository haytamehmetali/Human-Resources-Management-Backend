package owl.humanresourcesmanagement.dto.response;

import owl.humanresourcesmanagement.enums.user.EUserState;

public record PersonalStateResponseDto(
		Long id,
		String firstName,
		String lastName,
		EUserState userState
) {

}
