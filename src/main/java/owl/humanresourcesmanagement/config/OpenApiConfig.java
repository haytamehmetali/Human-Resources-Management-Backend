package owl.humanresourcesmanagement.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
	
	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
				.components(new Components()
						            .addSecuritySchemes("bearer-token",
						                                new SecurityScheme()
								                                .type(SecurityScheme.Type.HTTP)
								                                .scheme("bearer")
								                                .bearerFormat("JWT")
						            ))
				.info(new Info()
						      .title("Human-Resources-Management")
						      .description("Human-Resources-Management RestAPI")
						      .version("2025"))
				.addSecurityItem(new SecurityRequirement().addList("bearer-token"));
	}
}
