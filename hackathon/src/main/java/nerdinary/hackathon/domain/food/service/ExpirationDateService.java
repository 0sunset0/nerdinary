package nerdinary.hackathon.domain.food.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
@Slf4j
@Service
public class ExpirationDateService {
    private final Map<String, Map<String, Integer>> expirationMap = new HashMap<>();

    public LocalDate calculateExpiration(String foodCategory, String storageMethod, LocalDate purchaseDate) {
        try {
            // 상대 경로로 JSON 파일 로딩 (예: 프로젝트 루트 기준)
            File file = new File("./expireJson/expiration-data.json");

            ObjectMapper mapper = new ObjectMapper();
            expirationMap.putAll(mapper.readValue(file, new TypeReference<>() {}));

            log.info("소비기한 데이터 로드 완료 (상대경로)");
        } catch (Exception e) {
            log.error("소비기한 데이터 로드 실패", e);
        }

        //foodName과 storageMethod를 기준으로 값찾기
        Map<String, Integer> storageMap = expirationMap.getOrDefault(foodCategory, new HashMap<>());
        int days = storageMap.getOrDefault(storageMethod, 0);

        return purchaseDate.plusDays(days); // 예시
    }
}
