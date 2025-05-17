package nerdinary.hackathon.domain.rate;

import static nerdinary.hackathon.global.exception.ErrorCode.*;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import nerdinary.hackathon.domain.login.service.UserException;
import nerdinary.hackathon.domain.user.User;
import nerdinary.hackathon.domain.user.UserRepository;

@Component
@RequiredArgsConstructor
public class RateService {

	private final UserRepository userRepository;

	public double getUsedFoodRate(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new UserException(USER_NOT_FOUND));
		int usedCount = user.getUsedFoodCount();
		int totalCount = user.getTotalFoodCount();
		return calculateRate(usedCount, totalCount);
	}

	public static double calculateRate(int count, int totalCount) {
		if (totalCount == 0) {
			return 0.0;
		}
		return (double) count / totalCount;
	}
}
