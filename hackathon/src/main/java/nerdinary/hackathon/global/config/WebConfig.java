package nerdinary.hackathon.global.config;

import java.util.List;

import lombok.RequiredArgsConstructor;
import nerdinary.hackathon.domain.login.jwt.JwtArgumentResolver;
import nerdinary.hackathon.domain.login.service.AuthInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final AuthInterceptor authInterceptor;
	private final JwtArgumentResolver jwtArgumentResolver;

	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilterRegistrationBean() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.setMaxAge(1800L);

		config.setAllowedOriginPatterns(List.of(
				"https://frontend-two-pi-48.vercel.app",
				"http://localhost:5173",
				"http://localhost:8081",
				"http://localhost:8080",
				"https://goodluckynews.store",
				"http://goodluckynews.store"
		));

		config.addAllowedHeader("*");
		config.addAllowedMethod("*");

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		FilterRegistrationBean<CorsFilter> filterBean = new FilterRegistrationBean<>(new CorsFilter(source));
		filterBean.setOrder(0); // 가장 먼저 실행되도록

		return filterBean;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authInterceptor)
				.addPathPatterns("/api/**")
				.excludePathPatterns("/api/auth/login", "/api/auth/join", "/api/auth/reissue");
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(jwtArgumentResolver);
	}
}
