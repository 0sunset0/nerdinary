package nerdinary.hackathon.domain.login.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nerdinary.hackathon.domain.login.dto.reponse.LoginResponse;
import nerdinary.hackathon.domain.login.dto.reponse.ReissueResponse;
import nerdinary.hackathon.domain.login.dto.request.JoinRequest;
import nerdinary.hackathon.domain.login.dto.request.LoginRequest;
import nerdinary.hackathon.domain.login.dto.request.ReissueRequest;
import nerdinary.hackathon.domain.login.dto.reponse.JoinResponse;
import nerdinary.hackathon.domain.login.service.LoginService;
@Tag(name = "로그인 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class LoginController {

	private final LoginService loginService;

	@Operation(summary = "로그인")
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> loginOAuth(@Valid @RequestBody LoginRequest loginRequest) {
		return ResponseEntity.ok(loginService.login(loginRequest));
	}

	@Operation(summary = "회원가입")
	@PostMapping("/join")
	public ResponseEntity<JoinResponse> join(@Valid @RequestBody JoinRequest joinRequest) {
		return ResponseEntity.ok(loginService.join(joinRequest));
	}

	@Operation(summary = "토큰 재발급")
	@PostMapping("/reissue")
	public ResponseEntity<ReissueResponse> reissueToken(@Valid @RequestBody ReissueRequest reissueRequest) {
		return ResponseEntity.ok(loginService.reissue(reissueRequest.getRefreshToken()));
	}
}

