package nerdinary.hackathon.domain.login.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import nerdinary.hackathon.domain.login.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
}
