package nerdinary.hackathon.domain.login.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JoinResponse {
	private String accessToken;
	private String refreshToken;
}
