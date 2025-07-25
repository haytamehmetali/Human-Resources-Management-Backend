package owl.humanresourcesmanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import owl.humanresourcesmanagement.entity.User;
import owl.humanresourcesmanagement.enums.user.EUserState;
import owl.humanresourcesmanagement.repository.UserRepository;
import owl.humanresourcesmanagement.utility.JwtManager;
import owl.humanresourcesmanagement.exception.*;
import owl.humanresourcesmanagement.exception.Exception;

@Service
@RequiredArgsConstructor
public class TokenService {
	private final JwtManager jwtManager;
	private final UserRepository userRepository;
	
	public User getToken(String token) {
		if (jwtManager.validateToken(token).isEmpty()) {
			throw new Exception(ErrorType.INVALID_TOKEN);
		}
		
		User user = userRepository.findById(jwtManager.validateToken(token).get())
		                          .orElseThrow(() -> new Exception(ErrorType.USER_NOT_FOUND));
		
		if (user.getUserState() != EUserState.ACTIVE) {
			throw new Exception(ErrorType.ACCOUNT_DOESNT_ACTIVE);
		}
		
		return user;
	}
}
