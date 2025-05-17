package nerdinary.hackathon.domain.rate.rateService;

import static nerdinary.hackathon.global.exception.ErrorCode.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import nerdinary.hackathon.domain.food.repository.FoodRegisterRepository;
import nerdinary.hackathon.domain.rate.dto.RateResponse;
import nerdinary.hackathon.domain.login.service.UserException;
import nerdinary.hackathon.domain.user.User;
import nerdinary.hackathon.domain.user.UserRepository;
import org.springframework.stereotype.Component;
import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RateService {

	private final UserRepository userRepository;
	private final FoodRegisterRepository foodRegisterRepository;

	// JSON 데이터를 담을 리스트 (캐싱용)
	private List<Map<String, Object>> rateDataList;

	// 초기화 시 JSON 파일을 읽어서 rateDataList에 저장
	@PostConstruct
	public void init() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			File file = new File("./rateJson/rate-date.json"); // 현재 작업 디렉토리 기준

			rateDataList = mapper.readValue(file, new TypeReference<>() {});
			System.out.println("rate-data.json 로드 완료!");
		} catch (Exception e) {
			throw new RuntimeException("rate-data.json 파일 로드 실패", e);
		}
	}


	public RateResponse calculateUserRate(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserException(USER_NOT_FOUND));

		long usedCount = foodRegisterRepository.countByUserAndFoodStatus(user, "사용");
		long totalCount = foodRegisterRepository.countByUser(user);

		int consumptionRatePercent = 0;
		if (totalCount > 0) {
			consumptionRatePercent = (int) ((double) usedCount / totalCount * 100);
		}
		String consumptionRate = consumptionRatePercent + "%";

		// rateDataList에서 조건에 맞는 항목 찾기
		Map<String, Object> matchedRate = findMatchingRate(consumptionRatePercent);

		if (matchedRate == null) {
			// 조건에 맞는 데이터가 없으면 빈 값 반환
			return RateResponse.builder()
					.consumptionRate(consumptionRate)
					.fridgeComment("")
					.nearExpiredCount(0)
					.level(0)
					.typeName("")
					.foodBTI("")
					.foodBTIDetail("")
					.description("")
					.build();
		}

		// 각 필드 안전하게 추출
		String fridgeComment = (String) matchedRate.getOrDefault("fridgeComment", "");
		int level = (int) ((Number) matchedRate.getOrDefault("level", 0)).intValue();
		String typeName = (String) matchedRate.getOrDefault("typeName", "");
		String foodBTI = (String) matchedRate.getOrDefault("foodBTI", "");
		String foodBTIDetail = (String) matchedRate.getOrDefault("foodBTIDetail", "");
		String description = (String) matchedRate.getOrDefault("description", "");

		long expiredCount = foodRegisterRepository
				.countByUserAndExpirationDateBeforeAndFoodStatus(user, LocalDate.now(), "보관");

		return RateResponse.builder()
				.consumptionRate(consumptionRate)
				.fridgeComment(fridgeComment)
				.nearExpiredCount(expiredCount) // 현재 로직에 근거 없음
				.level(level)
				.typeName(typeName)
				.foodBTI(foodBTI)
				.foodBTIDetail(foodBTIDetail)
				.description(description)
				.build();
	}

	/**
	 * consumptionRatePercent에 맞는 rateData 항목을 찾는 메서드
	 */
	private Map<String, Object> findMatchingRate(int consumptionRatePercent) {
		for (Map<String, Object> rate : rateDataList) {
			Map<String, Object> conditions = (Map<String, Object>) rate.get("conditions");

			Integer min = (Integer) conditions.getOrDefault("consumptionRateMin", null);
			Integer max = (Integer) conditions.getOrDefault("consumptionRateMax", null);

			boolean minOk = min == null || consumptionRatePercent >= min;
			boolean maxOk = max == null || consumptionRatePercent <= max;

			if (minOk && maxOk) {
				return rate;
			}
		}
		return null;
	}
}
