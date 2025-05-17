package nerdinary.hackathon.domain.food.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ExpirationDateService {
    public LocalDate calculateExpiration(String foodName, String storageMethod, LocalDate purchaseDate) {
        // TODO: 소비기한 자동 계산 로직 추가
        return purchaseDate.plusDays(5); // 예시
    }
}
