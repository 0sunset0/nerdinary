package nerdinary.hackathon.domain.login.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import nerdinary.hackathon.domain.login.entity.User;
public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByEmail(String email);      // 이메일 중복 체크
	boolean existsByUserName(String userName); // 닉네임 중복 체크
	Optional<User> findByEmail(String email); // 이메일로 유저 찾기

	Optional<User> findByPassword(String password);
}
