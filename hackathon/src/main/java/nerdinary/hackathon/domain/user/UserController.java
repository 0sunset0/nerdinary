package nerdinary.hackathon.domain.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import nerdinary.hackathon.domain.login.jwt.JwtValidation;
@Tag(name = "유저 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

	private final UserService userService;

	@Operation(summary = "내 정보 조회 (사용 수, 폐기 수, 탄소 MBTI 결과)")
	@Parameter(name = "AccessToken: Bearer ", in = ParameterIn.HEADER, required = true)
	@GetMapping("/me")
	public ResponseEntity<UserInfoResponse> getUserInfo(
		@Parameter(hidden = true) @JwtValidation Long userId
	) {
		UserInfoResponse userInfo = userService.getUserInfo(userId);
		return ResponseEntity.ok(userInfo);
	}
}
