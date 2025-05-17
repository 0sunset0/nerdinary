package nerdinary.hackathon.domain.mbti;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import nerdinary.hackathon.domain.login.jwt.JwtValidation;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mbti")
public class MbtiController {
	private final MbtiService mbtiService;

	@GetMapping
	public ResponseEntity<MbtiResponse> getMyMbti(@JwtValidation Long userId) {
		// return ResponseEntity.ok(mbtiService.getMbti(userId));
		return null;
	}

	@GetMapping("/statistics")
	public ResponseEntity<FoodStatisticsResponse> getMyStatistics(@JwtValidation Long userId) {
		// return ResponseEntity.ok(mbtiService.getStatistics(userId));
		return null;
	}

	@GetMapping("/d-day")
	public ResponseEntity<Double> getFoodDDay(@JwtValidation Long userId) {
		// return ResponseEntity.ok(mbtiService.getAverageDDay(userId));
		return null;
	}
}
