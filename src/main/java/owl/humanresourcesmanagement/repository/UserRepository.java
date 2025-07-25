package owl.humanresourcesmanagement.repository;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import owl.humanresourcesmanagement.dto.request.UpdateProfileRequestDto;
import owl.humanresourcesmanagement.entity.User;
import owl.humanresourcesmanagement.enums.user.EUserState;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findOptionalByMail(String mail);
	
	boolean existsByMail(String mail);
	
	boolean existsByPhone(String phone);
	
	@Query("SELECT COUNT(u) FROM User u")
	Long countPersonal();
	
	@Query("SELECT COUNT(u) FROM User u WHERE u.userState = owl.humanresourcesmanagement.enums.user.EUserState.ACTIVE")
	Long countActivePersonal();
	
	@Query("""
		SELECT COUNT(u) FROM User u WHERE u.userState = owl.humanresourcesmanagement.enums.user.EUserState.ACTIVE
		AND u.employeeInformation.companyId = :companyId
	""")
	Long countActivePersonalByCompanyId(@Param("companyId") Long companyId);
	
	@Query("""
    	SELECT COUNT(DISTINCT u.id) FROM User u JOIN Permission p ON u.id = p.userId WHERE u.employeeInformation.companyId = :companyId
      	AND p.permissionState = owl.humanresourcesmanagement.enums.permissions.EPermissionState.APPROVED
      	AND CURRENT_DATE BETWEEN p.beginDate AND p.endDate
    """)
	Long countApprovedPermissionsTodayByCompanyId(@Param("companyId") Long companyId);
	
	@Query("select u.id from User u where u.employeeInformation.companyId = :companyId")
	List<Long> findUserIdByCompanyId(@Param("companyId") Long companyId);
	
	Page<User> findByIdIn(List<Long> userIds, Pageable pageable);
	
	List<User> findAllByEmployeeInformationCompanyIdAndUserState(Long companyId, EUserState userState);
	
	
}
