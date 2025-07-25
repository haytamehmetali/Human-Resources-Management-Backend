package owl.humanresourcesmanagement.init;

import owl.humanresourcesmanagement.entity.*;
import owl.humanresourcesmanagement.enums.user.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

public class UserInitializer {
	
	public static List<User> userInitializer() {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		List<User> users = new ArrayList<>();
		
		Map<Long, String> companyDomains = Map.of(
				1L, "technova.com",
				2L, "greensolutions.com",
				3L, "skynet.com",
				4L, "futuresoft.io",
				5L, "urbantech.co",
				6L, "ecoware.org",
				7L, "neonedge.ai",
				8L, "solarcore.com",
				9L, "codenation.dev",
				10L, "pixelpeak.com"
		);
		
		int userCounter = 1;
		
		for (long companyId = 1; companyId <= 10; companyId++) {
			String domain = companyDomains.get(companyId);
			String email = "manager" + companyId + "@" + domain;
			users.add(User.builder()
			              .employeeInformation(EmployeeInformation.builder()
			                                                      .companyId(companyId)
			                                                      .firstName("ManagerFirstName" + companyId)
			                                                      .lastName("ManagerLastName" + companyId)
			                                                      .build())
			              .mail(email)
			              .pendingMail(email)
			              .password(passwordEncoder.encode("Aaa12345!"))
			              .avatar("manager" + companyId + ".jpg")
			              .phone("0500111" + String.format("%04d", companyId))
			              .userState(EUserState.ACTIVE)
			              .role(EUserRole.MANAGER)
			              .build());
			userCounter++;
		}
		
		while (userCounter <= 50) {
			long companyId = ((userCounter - 1) % 10) + 1;
			String domain = companyDomains.get(companyId);
			String email = "user" + userCounter + "@" + domain;
			
			EUserRole role = (userCounter % 6 == 0) ? EUserRole.MANAGER : EUserRole.PERSONAL;
			
			EUserState state;
			if (userCounter % 5 == 0) {
				state = EUserState.DENIED;
			}
			else if (userCounter % 3 == 0) {
				state = EUserState.PENDING;
			}
			else {
				state = EUserState.ACTIVE;
			}
			
			users.add(User.builder()
			              .employeeInformation(EmployeeInformation.builder()
			                                                      .companyId(companyId)
			                                                      .firstName("FirstName" + userCounter)
			                                                      .lastName("LastName" + userCounter)
			                                                      .build())
			              .mail(email)
			              .pendingMail(email)
			              .password(passwordEncoder.encode("Aaa12345!"))
			              .avatar("person" + ((userCounter % 5) + 1) + ".jpg")
			              .phone("0500" + String.format("%07d", userCounter * 222))
			              .userState(state)
			              .role(role)
			              .build());
			
			userCounter++;
		}
		
		return users;
	}
}
