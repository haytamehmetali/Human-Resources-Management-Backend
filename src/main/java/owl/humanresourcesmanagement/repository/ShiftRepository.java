package owl.humanresourcesmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import owl.humanresourcesmanagement.entity.Shift;
import java.util.List;

public interface ShiftRepository extends JpaRepository<Shift, Long> {
	List<Shift> findAllByCompanyId(Long companyId);
	
	@Query("SELECT s FROM Shift s WHERE SIZE(s.assignedUserIds) > 0 AND s.companyId = :companyId")
	List<Shift> findAllAssignedShiftsByCompanyId(@Param("companyId") Long companyId);
	
	List<Shift> findAllByAssignedUserIdsContains(Long userId);
}
