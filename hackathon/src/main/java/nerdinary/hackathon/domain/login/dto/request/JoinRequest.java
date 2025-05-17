package nerdinary.hackathon.domain.login.dto.request;

import java.util.PrimitiveIterator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JoinRequest {
	private String userName;
	private String email;
	private String password;
}
