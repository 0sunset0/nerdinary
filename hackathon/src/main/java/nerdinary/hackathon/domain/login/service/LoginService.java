package nerdinary.hackathon.domain.login.service;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import nerdinary.hackathon.domain.login.dto.reponse.JoinResponse;
import nerdinary.hackathon.domain.login.dto.reponse.LoginResponse;
import nerdinary.hackathon.domain.login.dto.reponse.ReissueResponse;
import nerdinary.hackathon.domain.login.dto.request.JoinRequest;
import nerdinary.hackathon.domain.login.dto.request.LoginRequest;

@Service
@Transactional
public class LoginService {
	public LoginResponse login(LoginRequest loginRequest) {
		return null;
	}

	public JoinResponse join(@Valid JoinRequest joinRequest) {
		return null;
	}

	public ReissueResponse reissue(String refreshToken) {
		return null;
	}
}
