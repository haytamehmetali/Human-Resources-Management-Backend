package owl.humanresourcesmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import owl.humanresourcesmanagement.entity.Company;
import owl.humanresourcesmanagement.enums.company.ECompanyState;
import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
	Optional<Company> findByName(String name);
	
	List<Company> findAllByCompanyState(ECompanyState companyState);
	
	@Query("SELECT COUNT(c) FROM Company c ")
	Long countCompanies();
	
	@Query("SELECT COUNT(c) FROM Company c WHERE c.companyState = owl.humanresourcesmanagement.enums.company.ECompanyState.ACCEPTED")
	Long countActiveCompanies();
}
