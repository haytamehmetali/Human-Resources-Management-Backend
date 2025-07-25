package owl.humanresourcesmanagement.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import owl.humanresourcesmanagement.repository.RefreshTokenRepository;
import java.time.Instant;
import java.util.Optional;

@Component
public class JwtManager {
	@Autowired
	RefreshTokenRepository refreshTokenRepository;
	
	@Value("${java17.jwt.secret-key}") // @Value("${java17.jwt.secret-key}")
	private String secretKey;
	
	@Value("${java17.jwt.issuer}") // @Value("${java17.jwt.issuer}")
	private String issuer;
	
	private final long accessTokenExpiration =  60L * 1000 * 60;  // 60L * 1000 * 60 -> 1 saat
	private final long refreshTokenExpiration = 60L * 1000 * 60;
	
	
	public String generateToken(Long authId) {
		Algorithm algorithm = Algorithm.HMAC512(secretKey);
		String token = JWT.create()
		                  .withAudience()
		                  .withIssuer(issuer)
		                  .withIssuedAt(Instant.now())
		                  .withExpiresAt(Instant.now().plusSeconds(accessTokenExpiration))
		                  .withClaim("authId", authId)
		                  .withClaim("role", "role")
		                  .withClaim("key", "key")
		                  .sign(algorithm);
		return token;
	}
	
	public String generateAccessToken(Long authId, String role) {
		return JWT.create()
		          .withIssuer(issuer)
		          .withIssuedAt(Instant.now())
		          .withExpiresAt(Instant.now().plusSeconds(accessTokenExpiration))
		          .withClaim("authId", authId)
		          .withClaim("role", role)
		          .sign(Algorithm.HMAC512(secretKey));
	}
	
	public Optional<Long> validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC512(secretKey);
			JWTVerifier verifier = JWT.require(algorithm).build();
			DecodedJWT decodedJWT = verifier.verify(token);
			if (decodedJWT == null) {
				return Optional.empty();
			}
			Long authId = decodedJWT.getClaim("authId").asLong();
			return Optional.of(authId);
		} catch (IllegalArgumentException | JWTVerificationException e) {
			System.out.println(e.getMessage());
			return Optional.empty();
		}
	}
	
	@Transactional
	public void deleteRefreshToken(String token) {
		refreshTokenRepository.deleteByToken(token);
	}
	
	public Optional<String> getRoleFromToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC512(secretKey);
			JWTVerifier verifier = JWT.require(algorithm).build();
			DecodedJWT decodedJWT = verifier.verify(token);
			if (decodedJWT == null) {
				return Optional.empty();
			}
			String role = decodedJWT.getClaim("role").asString();
			return Optional.ofNullable(role);
		} catch (IllegalArgumentException | JWTVerificationException e) {
			System.out.println(e.getMessage());
			return Optional.empty();
		}
	}
}
