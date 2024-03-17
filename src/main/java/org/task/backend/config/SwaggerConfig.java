package org.task.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("API Documentation")
						.version("v1"))
				.addSecurityItem(new SecurityRequirement().addList("api_key"))
				.components(new io.swagger.v3.oas.models.Components()
						.addSecuritySchemes("api_key", new SecurityScheme()
								.name("Authorization")
								.type(SecurityScheme.Type.APIKEY)
								.in(SecurityScheme.In.HEADER)));
	}

	@Bean
	public GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder()
				.group("springshop-public")
				.pathsToMatch("/**")
				.build();
	}
}
