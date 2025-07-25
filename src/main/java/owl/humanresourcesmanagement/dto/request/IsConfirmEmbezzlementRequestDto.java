package owl.humanresourcesmanagement.dto.request;

public record IsConfirmEmbezzlementRequestDto(
		Long id,
		Boolean isConfirm,
		String rejectReason // sadece isConfirm = false ise geçerli olacak
) {
}
