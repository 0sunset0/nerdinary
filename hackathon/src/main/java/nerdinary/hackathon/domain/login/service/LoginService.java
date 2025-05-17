package nerdinary.hackathon.domain.login.service;


import static nerdinary.hackathon.global.exception.ErrorCode.*;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import nerdinary.hackathon.domain.login.dto.reponse.JoinResponse;
import nerdinary.hackathon.domain.login.dto.reponse.LoginResponse;
import nerdinary.hackathon.domain.login.dto.reponse.ReissueResponse;
import nerdinary.hackathon.domain.login.dto.request.JoinRequest;
import nerdinary.hackathon.domain.login.dto.request.LoginRequest;
import nerdinary.hackathon.domain.login.entity.User;
import nerdinary.hackathon.domain.login.jwt.JwtUtil;
import nerdinary.hackathon.domain.login.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {
	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;


	public LoginResponse login(LoginRequest loginRequest) {
		//회원가입 되어 있는 확인
		String email = loginRequest.getEmail();
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new UserException(USER_NOT_FOUND));

		//로그인 처리
		String password = loginRequest.getPassword();
		if (!user.getPassword().equals(password)) {
			throw new UserException(INVALID_INPUT_VALUE);
		}

		//토큰 발급
		String accessToken = jwtUtil.createAccessToken(password);
		String refreshToken = jwtUtil.createRefreshToken(password);
		//리프레쉬 토큰 저장
		user.updateRefreshToken(refreshToken);
		//로그인 성공
		return new LoginResponse(accessToken, refreshToken);
	}

	public JoinResponse join(JoinRequest joinRequest) {
		//이메일 중복 검사
		String email = joinRequest.getEmail();
		checkDuplicateEmail(email);

		//회원 가입
		User user = User.createUser(joinRequest.getEmail(), joinRequest.getPassword());
		userRepository.save(user);

		//토큰 발급
		String password = joinRequest.getPassword();
		String accessToken = jwtUtil.createAccessToken(password);
		String refreshToken = jwtUtil.createRefreshToken(password);

		//리프레쉬 토큰 저장
		user.updateRefreshToken(refreshToken);
		return new JoinResponse(accessToken, refreshToken);
	}

	public ReissueResponse reissue(String refreshToken) {
		//리프레쉬 토큰 검증
		if (!jwtUtil.validateToken(refreshToken)) {
			throw new UserException(INVALID_TOKEN);
		}

		//리프레쉬 토큰으로 유저 찾기
		String password = jwtUtil.getPasswordFromToken(refreshToken);
		User user = userRepository.findByPassword(password)
			.orElseThrow(() -> new UserException(USER_NOT_FOUND));

		//리프레쉬 토큰 일치 여부 확인
		if (!refreshToken.equals(user.getRefreshToken())) {
			throw new UserException(INVALID_TOKEN);
		}

		//토큰 재발급
		String accessToken = jwtUtil.createAccessToken(password);
		return new ReissueResponse(accessToken, refreshToken);
	}

	private void checkDuplicateEmail(String email) {
		if (userRepository.existsByEmail(email)) {
			throw new UserException(DUPLICATE_EMAIL);
		}
	}
}
