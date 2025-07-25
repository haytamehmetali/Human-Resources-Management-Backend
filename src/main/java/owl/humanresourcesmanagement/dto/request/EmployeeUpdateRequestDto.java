package owl.humanresourcesmanagement.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import owl.humanresourcesmanagement.enums.user.*;
import java.time.LocalDate;
import static owl.humanresourcesmanagement.constant.RegexConstants.*;

public record EmployeeUpdateRequestDto(
		String firstName,
		String lastName,
		String identityNumber,
		LocalDate dateOfBirth,
		
		@Pattern(regexp = PHONE_REGEX_E164, message = "Telefon numarası uluslararası formatta olmalıdır. Örn: +905xxxxxxxxx")
		String phone,
		
		String address,
		EGender gender,
		EMaritalStatus maritalStatus,
		
		@Email(message = "Geçerli bir e-posta adresi giriniz.")
		String mail,
		
		EEducationLevel educationLevel,
		String position,
		EWorkType workType,
		LocalDate dateOfEmployment,
		LocalDate dateOfTermination,
		
		@DecimalMin(value = "0.0", inclusive = false, message = "Maaş pozitif bir değer olmalıdır.")
		Double salary,
		
		EEmploymentStatus employmentStatus
) {
}
