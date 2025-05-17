package nerdinary.hackathon.domain.login.service;

import static nerdinary.hackathon.global.exception.ErrorCode.*;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import nerdinary.hackathon.domain.login.jwt.JwtUtil;
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

	private static final List<String> EXCLUDE_PATHS = List.of("/api/auth/login", "/api/auth/join", "/api/auth/reissue");

	private final JwtUtil jwtUtil;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String path = request.getRequestURI();

		// 인증 제외 경로는 그냥 통과
		if (EXCLUDE_PATHS.contains(path)) {
			return true;
		}

		String authHeader = request.getHeader("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			throw new UserException(UNAUTHORIZED);
		}

		String token = authHeader.substring(7); // "Bearer " 이후 부분

		if (!jwtUtil.validateToken(token)) {
			throw new UserException(UNAUTHORIZED);
		}
		return true;
	}
}
