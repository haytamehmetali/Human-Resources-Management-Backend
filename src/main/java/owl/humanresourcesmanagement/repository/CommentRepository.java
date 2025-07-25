package owl.humanresourcesmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import owl.humanresourcesmanagement.entity.Comment;
import owl.humanresourcesmanagement.enums.company.ECommentStatus;
import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	Optional<Comment> findByCompanyId(Long companyId);
	
	List<Comment> findByStatus(ECommentStatus status);
}
