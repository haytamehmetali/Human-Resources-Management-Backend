package owl.humanresourcesmanagement.dto.request;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import static owl.humanresourcesmanagement.constant.RegexConstants.*;

public record RegisterRequestDto(
		@NotBlank(message = "E-mail cannot empty!")
		@Email(message = "Enter valid e-mail address!")
		String mail,
		
		@NotBlank(message = "Password cannot empty!")
		@Size(min = 8, max = 64, message = "Password must between 8 to 64 characters!")
		@Pattern(regexp = PASSWORD_REGEX, message = "Password must contain 1 uppercase/lowercase letter, 1 number and 1 special character!")
		String password,
		
		@NotBlank(message = "Re-password cannot empty!")
		String rePassword,
		
		@NotBlank(message = "First name cannot empty!")
		@Size(max = 50, message = "First name must max 50 characters!")
		String firstName,
		
		@NotBlank(message = "Last name cannot empty!")
		@Size(max = 50, message = "Last name must max 50 characters!")
		String lastName,
		
		@NotBlank(message = "Phone cannot empty!")
		@Pattern(regexp = PHONE_REGEX_E164, message = "Phone number must international format!")
		String phone,
		
		@NotBlank(message = "Company name cannot empty!")
		String companyName,
		
		@NotNull(message = "Date of employment cannot be null!")
		LocalDate dateOfEmployment
) {

}
