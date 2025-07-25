package owl.humanresourcesmanagement.dto.request;

import jakarta.validation.constraints.Pattern;
import static owl.humanresourcesmanagement.constant.RegexConstants.PHONE_REGEX_E164;

public record EmployeeUpdateProfileRequestDto(
		@Pattern(regexp = PHONE_REGEX_E164, message = "Telefon numarası uluslararası formatta olmalıdır. Örn: +905xxxxxxxxx")
		String phone,
		
		String address
) {
}
