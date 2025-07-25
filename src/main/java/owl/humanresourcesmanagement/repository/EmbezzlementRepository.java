package owl.humanresourcesmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import owl.humanresourcesmanagement.entity.Embezzlement;
import owl.humanresourcesmanagement.enums.embezzlement.EEmbezzlementState;
import java.util.List;

public interface EmbezzlementRepository extends JpaRepository<Embezzlement, Long> {
	List<Embezzlement> findAllByUserId(Long id);
	
	@Query("""
            SELECT e FROM Embezzlement e WHERE e.embezzlementState = :embezzlementState AND e.userId
            IN (SELECT u.id FROM User u WHERE u.employeeInformation.companyId = :companyId)
    """)
	List<Embezzlement> findAllByEmbezzlementState(@Param("companyId") Long companyId , @Param("embezzlementState") EEmbezzlementState embezzlementState);
	
	@Query("""
           SELECT e FROM Embezzlement e WHERE e.embezzlementState <> :excludedState AND e.userId
           IN (SELECT u.id FROM User u WHERE u.employeeInformation.companyId = :companyId)
    """)
	List<Embezzlement> findAllByEmbezzlementStateNot( @Param("companyId") Long companyId, @Param("excludedState") EEmbezzlementState excludedState);
	
	@Query("SELECT e FROM Embezzlement e WHERE e.companyId = :companyId")
	List<Embezzlement> findAllByCompanyId(@Param("companyId") Long companyId);
}
