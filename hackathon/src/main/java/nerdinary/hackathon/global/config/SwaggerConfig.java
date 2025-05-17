package nerdinary.hackathon.global.config;

import java.util.List;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

	@Bean
	public GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder()
			.group("v1")
			.pathsToMatch("/api/**")
			.build();
	}
	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
			.servers(List.of(
				new Server().url("https://goodluckynews.store")
			))
			.info(new Info()
				.title("Hackathon API")
				.description("Carbon MBTI API Documentation")
				.version("v1"))
			.components(new Components()
				.addSecuritySchemes("accessToken", new SecurityScheme()
					.type(SecurityScheme.Type.HTTP)
					.scheme("bearer")
					.bearerFormat("JWT")
					.name("Authorization")
				)
				.addSecuritySchemes("refreshToken", new SecurityScheme()
					.type(SecurityScheme.Type.APIKEY)
					.in(SecurityScheme.In.HEADER)
					.name("RefreshToken")
					.bearerFormat("JWT")
				)
			)
			.addSecurityItem(new SecurityRequirement().addList("accessToken"));
	}
}

