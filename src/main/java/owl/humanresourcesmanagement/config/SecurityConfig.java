package owl.humanresourcesmanagement.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import owl.humanresourcesmanagement.enums.EAdminRole;
import owl.humanresourcesmanagement.enums.user.EUserRole;

import static owl.humanresourcesmanagement.constant.EndPoints.*;

@Configuration
@Slf4j
public class SecurityConfig {
	
	@Bean
	public JWTTokenFilter getJwtTokenFilter() {
		return new JWTTokenFilter();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(req -> {
			req.requestMatchers(
					   "/swagger-ui/**", "/api-docs/**")
			   .permitAll()
			   .requestMatchers(ADMIN + "/**").hasAnyAuthority(EAdminRole.ADMIN.toString(), EAdminRole.SUPER_ADMIN.toString())
			   .requestMatchers(COMPANY + "/**").hasAuthority(EUserRole.MANAGER.toString())
			   .requestMatchers(EMPLOYEE + "/**").hasAnyAuthority(EUserRole.MANAGER.toString(), EUserRole.PERSONAL.toString())
			   .requestMatchers(USER + "/**").hasAnyAuthority(EUserRole.MANAGER.toString(), EUserRole.PERSONAL.toString())
			   .anyRequest().permitAll();
		});
		http.csrf(AbstractHttpConfigurer::disable);
		http.addFilterBefore(getJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
