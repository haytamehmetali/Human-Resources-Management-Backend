package owl.humanresourcesmanagement.dto.request;

import jakarta.validation.constraints.*;
import owl.humanresourcesmanagement.enums.user.*;
import java.time.LocalDate;
import static owl.humanresourcesmanagement.constant.RegexConstants.PASSWORD_REGEX;
import static owl.humanresourcesmanagement.constant.RegexConstants.PHONE_REGEX_E164;

public record EmployeeRequestDto(
		@NotBlank(message = "Ad alanı boş bırakılamaz.")
		String firstName,
		
		@NotBlank(message = "Soyad alanı boş bırakılamaz.")
		String lastName,
		
		@NotBlank(message = "E-posta adresi zorunludur.")
		@Email(message = "Geçerli bir e-posta adresi giriniz.")
		String mail,
		
		@NotBlank(message = "Şifre zorunludur.")
		@Size(min = 8, max = 64, message = "Şifre en az 8, en fazla 64 karakter olmalıdır.")
		@Pattern(regexp = PASSWORD_REGEX, message = "Şifre en az bir büyük harf, bir küçük harf, bir rakam ve bir özel karakter (@#$%^&+=*!.,?/ vb.) içermelidir.")
		String password,
		
		@NotBlank(message = "TC kimlik numarası zorunludur.")
		String identityNumber,
		
		@NotNull(message = "Doğum tarihi zorunludur.")
		LocalDate dateOfBirth,
		
		@NotBlank(message = "Telefon numarası boş bırakılamaz.")
		@Pattern(regexp = PHONE_REGEX_E164, message = "Telefon numarası uluslararası formatta olmalıdır. Örn: +905xxxxxxxxx")
		String phone,
		
		@NotBlank(message = "Adres bilgisi zorunludur.")
		String address,
		
		@NotNull(message = "Cinsiyet seçilmelidir.")
		EGender gender,
		
		@NotNull(message = "Medeni durum seçilmelidir.")
		EMaritalStatus maritalStatus,
		
		@NotNull(message = "Eğitim durumu seçilmelidir.")
		EEducationLevel educationLevel,
		
		@NotBlank(message = "Pozisyon bilgisi girilmelidir.")
		String position,
		
		@NotNull(message = "Çalışma tipi seçilmelidir.")
		EWorkType workType,
		
		@NotNull(message = "İşe başlama tarihi zorunludur.")
		LocalDate dateOfEmployment,
		
		LocalDate dateOfTermination,
		
		@NotNull(message = "Maaş bilgisi girilmelidir.")
		@DecimalMin(value = "0.0", inclusive = false, message = "Maaş pozitif bir değer olmalıdır.")
		Double salary,
		
		@NotNull(message = "Çalışma durumu seçilmelidir.")
		EUserState userState
) {
}
