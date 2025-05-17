package nerdinary.hackathon.domain.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import nerdinary.hackathon.domain.login.jwt.JwtValidation;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

	private final UserService userService;

	@GetMapping("/me")
	public ResponseEntity<UserInfoResponse> getUserInfo(@JwtValidation Long userId) {
		UserInfoResponse userInfo = userService.getUserInfo(userId);
		return ResponseEntity.ok(userInfo);
	}
}
