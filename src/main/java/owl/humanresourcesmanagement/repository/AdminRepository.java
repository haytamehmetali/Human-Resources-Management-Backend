package owl.humanresourcesmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import owl.humanresourcesmanagement.entity.Admin;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
	
	
	Optional<Admin> findOptionalByUsername(String username);
}
