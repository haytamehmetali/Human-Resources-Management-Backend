package owl.humanresourcesmanagement.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import owl.humanresourcesmanagement.utility.JwtManager;

import java.io.IOException;

public class JWTTokenFilter extends OncePerRequestFilter {
	@Autowired
	private JwtManager jwtManager;
	
	@Autowired
	private JwtUserDetails jwtUserDetails;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws
	                                                                                                                   ServletException,
	                                                                                                                   IOException {
		final String authorizationHeader = request.getHeader("Authorization");
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			String token = authorizationHeader.substring(7);
			
			// Burada sadece userId değil, token ile userdetails alıyoruz
			UserDetails userDetails = jwtUserDetails.loadUserByToken(token);
			
			if (userDetails != null) {
				UsernamePasswordAuthenticationToken authenticationToken
						= new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}
		filterChain.doFilter(request, response);
	}
}
