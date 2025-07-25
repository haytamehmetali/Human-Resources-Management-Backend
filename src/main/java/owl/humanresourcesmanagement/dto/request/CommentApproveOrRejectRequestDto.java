package owl.humanresourcesmanagement.dto.request;

import owl.humanresourcesmanagement.enums.company.ECommentStatus;

public record CommentApproveOrRejectRequestDto(
		ECommentStatus status

) {
}
