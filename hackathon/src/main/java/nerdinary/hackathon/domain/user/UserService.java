package nerdinary.hackathon.domain.user;

import static nerdinary.hackathon.global.exception.ErrorCode.*;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import nerdinary.hackathon.domain.login.service.UserException;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;

	public UserInfoResponse getUserInfo(Long userId) {
		User user = findUser(userId);
		return UserInfoResponse.from(user);
	}

	public User findUser(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new UserException(USER_NOT_FOUND));
	}
}
