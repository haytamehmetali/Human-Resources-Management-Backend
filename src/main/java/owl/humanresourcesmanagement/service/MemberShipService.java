package owl.humanresourcesmanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import owl.humanresourcesmanagement.entity.MemberShip;
import owl.humanresourcesmanagement.enums.company.ECompanyMembershipType;
import owl.humanresourcesmanagement.repository.MemberShipRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberShipService {
	private final MemberShipRepository memberShipRepository;
	
	public void createMemberShip(Long companyId) {
		MemberShip memberShip = MemberShip.builder()
		                                  .companyId(companyId)
		                                  .companyMembershipType(ECompanyMembershipType.NONE)
		                                  .isMembershipActive(false)
		                                  .build();
		memberShipRepository.save(memberShip);
	}
	
	public void createOrFindMemberShip(Long companyId) {
		Optional<MemberShip> memberShipOptional = memberShipRepository.findByCompanyId(companyId);
		
		if (memberShipOptional.isEmpty()) {
			createMemberShip(companyId);
		}
	}
	
}
