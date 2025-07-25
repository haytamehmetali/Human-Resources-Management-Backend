package owl.humanresourcesmanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import owl.humanresourcesmanagement.entity.RefreshToken;
import owl.humanresourcesmanagement.repository.RefreshTokenRepository;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
	private final RefreshTokenRepository refreshTokenRepository;
	
	public RefreshToken createRefreshToken(Long userId) {
		String token = UUID.randomUUID().toString();
		
		RefreshToken refreshToken = RefreshToken.builder()
		                                        .authId(userId)
		                                        .token(token)
		                                        .expiryDate(LocalDateTime.now().plusDays(7))
		                                        .build();
		
		return refreshTokenRepository.save(refreshToken);
	}
	
}
