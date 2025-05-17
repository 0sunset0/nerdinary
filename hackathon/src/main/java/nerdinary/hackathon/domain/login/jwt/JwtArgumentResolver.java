package nerdinary.hackathon.domain.login.jwt;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import lombok.RequiredArgsConstructor;
import nerdinary.hackathon.domain.login.service.UserException;
import nerdinary.hackathon.global.exception.ErrorCode;

@Component
@RequiredArgsConstructor
public class JwtArgumentResolver implements HandlerMethodArgumentResolver {
	private final JwtUtil jwtUtil;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(JwtValidation.class) &&
			parameter.getParameterType().equals(Long.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
		ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest,
		WebDataBinderFactory binderFactory) {

		String authHeader = webRequest.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			throw new UserException(ErrorCode.INVALID_TOKEN);
		}

		String token = authHeader.substring(7);
		if (!jwtUtil.validateToken(token)) {
			throw new UserException(ErrorCode.INVALID_TOKEN);
		}

		// 여기서 userId 추출
		return jwtUtil.getUserIdFromToken(token); // ⇒ Long
	}
}
