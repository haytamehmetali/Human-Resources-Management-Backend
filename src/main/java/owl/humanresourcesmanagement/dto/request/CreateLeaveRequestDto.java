package owl.humanresourcesmanagement.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import owl.humanresourcesmanagement.enums.permissions.EPermissionType;
import java.time.LocalDate;

public record CreateLeaveRequestDto(
		@NotNull(message = "Başlangıç tarihi boş olamaz.")
		@FutureOrPresent(message = "Başlangıç tarihi bugünden önce olamaz.")
		LocalDate beginDate,
		
		@NotNull(message = "Bitiş tarihi boş olamaz.")
		@FutureOrPresent(message = "Bitiş tarihi bugünden önce olamaz.")
		LocalDate endDate,
		
		@NotNull(message = "İzin türü boş olamaz.")
		EPermissionType permissionType, // Enum olarak
		
		@Size(max = 500, message = "Açıklama en fazla 500 karakter olabilir.")
		String description
) {
}
