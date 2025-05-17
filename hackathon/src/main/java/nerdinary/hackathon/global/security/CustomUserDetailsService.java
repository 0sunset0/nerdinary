package nerdinary.hackathon.global.security;

import static nerdinary.hackathon.global.exception.ErrorCode.*;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import nerdinary.hackathon.domain.login.entity.User;
import nerdinary.hackathon.domain.login.repository.UserRepository;
import nerdinary.hackathon.domain.login.service.UserException;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new UserException(UNAUTHORIZED));
		return null;

	}
}