package nerdinary.hackathon.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserInfoResponse {

	private Integer usedFoodCount;
	private String mbti;

	public static UserInfoResponse from(User user) {
		return new UserInfoResponse(
			user.getUsedFoodCount(),
			user.getMbti()
		);
	}
}
