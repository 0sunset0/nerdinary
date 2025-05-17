package nerdinary.hackathon.domain.login.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReissueResponse {

	private String accessToken;
	private String refreshToken;
}
