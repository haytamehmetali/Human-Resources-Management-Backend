package owl.humanresourcesmanagement.dto.response;

import java.util.List;

public record HomePageContentResponseDto(
		String title,
		String content,
		List<String> highlights
) {
}
