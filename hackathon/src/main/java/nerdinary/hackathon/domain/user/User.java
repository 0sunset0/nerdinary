package nerdinary.hackathon.domain.user;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;
	private String userName;
	private String email;
	private String password;
	private Integer totalFoodCount;
	private Integer usedFoodCount;

	@Column(length = 255)
	private String mbti;

	private Boolean isActive;
	@Column(updatable = false)
	private LocalDateTime createdAt;
	private LocalDateTime updateAt;
	private String refreshToken;


	public User(String email, String password) {
		this.email = email;
		this.password = password;
		this.totalFoodCount = 0;
		this.usedFoodCount = 0;
	}

	public static User createUser(String email, String password) {
		return new User(email, password);
	}

	public void updateRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updateAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updateAt = LocalDateTime.now();
	}

	public void plusUsedCount() {
		this.usedFoodCount += 1;
	}


	public void plusTotalFoodCount() {
		this.totalFoodCount += 1;
	}
}
