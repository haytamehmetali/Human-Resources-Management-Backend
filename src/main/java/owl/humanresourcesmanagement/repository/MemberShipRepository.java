package owl.humanresourcesmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import owl.humanresourcesmanagement.entity.MemberShip;
import java.util.Optional;

public interface MemberShipRepository extends JpaRepository<MemberShip, Long> {
	Optional<MemberShip> findByCompanyId(Long companyId);
	
}
