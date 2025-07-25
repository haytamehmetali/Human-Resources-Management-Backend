package owl.humanresourcesmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import static owl.humanresourcesmanagement.constant.RegexConstants.*;

public record ChangePasswordRequestDto(
		@NotBlank(message = "Current password cannot empty!")
		String oldPassword,
		
		@NotBlank(message = "New password cannot empty!")
		@Size(min = 8, max = 64, message = "Şifre en az 8, en fazla 64 karakter olmalıdır.")
		@Pattern(regexp = PASSWORD_REGEX, message = "Şifrede en az bir büyük harf, bir küçük harf, bir rakam ve bir özel karakter (@#$%^&+=*!.,?/ vb.) olmalıdır.")
		String newPassword,
		
		@NotBlank(message = "Re-new password cannot empty!")
		@Size(min = 8, max = 64, message = "Şifre en az 8, en fazla 64 karakter olmalıdır.")
		@Pattern(regexp = PASSWORD_REGEX, message = "Şifrede en az bir büyük harf, bir küçük harf, bir rakam ve bir özel karakter (@#$%^&+=*!.,?/ vb.) olmalıdır.")
		String reNewPassword
) {
}
