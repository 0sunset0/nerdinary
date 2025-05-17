package nerdinary.hackathon.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
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
			.info(new Info()
				.title("Hackathon API")
				.description("Carbon MBTI API Documentation")
				.version("v1"))
			.components(new Components()
				.addSecuritySchemes("accessToken", new SecurityScheme()
					.name("Authorization") // Swagger UI에서 보이는 이름
					.type(SecurityScheme.Type.HTTP)
					.scheme("bearer")
					.bearerFormat("JWT")
				)
				.addSecuritySchemes("refreshToken", new SecurityScheme()
					.name("RefreshToken")
					.type(SecurityScheme.Type.APIKEY)
					.in(SecurityScheme.In.HEADER)
					.bearerFormat("JWT") // 선택 사항
				)
			)
			.addSecurityItem(new SecurityRequirement().addList("accessToken")); // 기본 인증 요구
	}
}
