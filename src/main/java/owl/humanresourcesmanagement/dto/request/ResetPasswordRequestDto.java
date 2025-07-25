package owl.humanresourcesmanagement.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import static owl.humanresourcesmanagement.constant.RegexConstants.*;

public record ResetPasswordRequestDto(
		@NotBlank(message = "Token boş olamaz.")
		String token,
		
		@NotBlank(message = "E-posta boş olamaz.")
		@Email(message = "Geçerli bir e-posta adresi giriniz.")
		String mail,
		
		@NotBlank(message = "Şifre sıfırlama kodu boş olamaz.")
		String resetCode,
		
		@NotBlank(message = "Yeni şifre boş olamaz.")
		@Size(min = 8, max = 64, message = "Şifre en az 8, en fazla 64 karakter olmalıdır.")
		@Pattern(regexp = PASSWORD_REGEX, message = "Şifrede en az bir büyük harf, bir küçük harf, bir rakam ve bir özel karakter (@#$%^&+=*!.,?/ vb.) olmalıdır.")
		String newPassword,
		
		@NotBlank(message = "Şifre tekrarı boş olamaz.")
		@Size(min = 8, max = 64, message = "Şifre en az 8, en fazla 64 karakter olmalıdır.")
		@Pattern(regexp = PASSWORD_REGEX, message = "Şifrede en az bir büyük harf, bir küçük harf, bir rakam ve bir özel karakter (@#$%^&+=*!.,?/ vb.) olmalıdır.")
		String reNewPassword
) {
	public ResetPasswordRequestDto {
		if (!newPassword.equals(reNewPassword)) {
			throw new IllegalArgumentException("Yeni şifre ve şifre tekrarı eşleşmiyor.");
		}
	}
}
