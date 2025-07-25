package owl.humanresourcesmanagement.init;

import owl.humanresourcesmanagement.repository.*;
import jakarta.annotation.PostConstruct;
import lombok.*;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {
	private final UserRepository userRepository;
	private final AdminRepository adminRepository;
	private final CompanyRepository companyRepository;
	private final PermissionRepository permissionRepository;
	private final PublicHolidaysRepository publicHolidaysRepository;
	private final CommentRepository commentRepository;
	private final PersonalSpendingRepository personalSpendingRepository;
	private final EmbezzlementRepository embezzlementRepository;
	private final ShiftRepository shiftRepository;

	@PostConstruct
	public void init() {
		if (userRepository.count() == 0) {
			userRepository.saveAll(UserInitializer.userInitializer());
		}

		if (adminRepository.count() == 0) {
			adminRepository.saveAll(AdminInitializer.adminInitializer());
		}

		if (companyRepository.count() == 0) {
			companyRepository.saveAll(CompanyInitializer.companyInitializer());
		}
		
		if (permissionRepository.count() == 0) {
			permissionRepository.saveAll(PermissionInitializer.permissionInitializer());
		}

		if (publicHolidaysRepository.count() == 0) {
			publicHolidaysRepository.saveAll(PublicHolidaysInitializer.publicHolidayInitializer());
		}

		if (personalSpendingRepository.count() == 0) {
			personalSpendingRepository.saveAll(SpendingInitializer.spendingInitializer());
		}

		if (commentRepository.count() == 0) {
			commentRepository.saveAll(CommentInitializer.commentInitializer());
		}

		if (embezzlementRepository.count() == 0) {
			embezzlementRepository.saveAll(EmbezzlementDataInitializer.embezzlementInitializer());
		}

		if (shiftRepository.count() == 0) {
			shiftRepository.saveAll(ShiftInitializer.shiftInitializer());
		}
		
		System.out.println("✅ DataInitializer data has been loaded successfully!");
	}
}
