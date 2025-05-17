package nerdinary.hackathon.domain.food.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import nerdinary.hackathon.domain.food.dto.FoodRegisterRequest;
import nerdinary.hackathon.domain.food.dto.FoodRegisterResponse;
import nerdinary.hackathon.domain.food.entity.Food;
import nerdinary.hackathon.domain.food.entity.FoodRegister;
import nerdinary.hackathon.domain.food.repository.FoodRegisterRepository;
import nerdinary.hackathon.domain.food.repository.FoodRepository;

import nerdinary.hackathon.domain.user.User;
import nerdinary.hackathon.domain.user.UserRepository;
import nerdinary.hackathon.global.exception.CustomException;
import nerdinary.hackathon.global.exception.ErrorCode;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;
    private final FoodRegisterRepository foodRegisterRepository;
    private final UserRepository userRepository;
    private final ExpirationDateService expirationDateService;

    @Override
    @Transactional
    public FoodRegisterResponse registerFood(FoodRegisterRequest request, Long userId) {
        // 1. 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 2. 음식명으로 중복 확인
        Food food = foodRepository.findByFoodName(request.getFoodName())
                .orElseGet(() -> foodRepository.save(
                        new Food(request.getFoodName(), request.getFoodCategory())
                ));

        // 3. 구매일 설정
        LocalDate purchaseDate = Optional.ofNullable(request.getPurchaseDate())
                .orElse(LocalDate.now());

        // 4. 소비기한 설정
        LocalDate expirationDate = getLocalDate(request, request.getExpirationDate());

        // 5. 음식 등록 저장
        FoodRegister register = FoodRegister.builder()
                .purchaseDate(purchaseDate)
                .expirationDate(expirationDate)
                .storageMethod(request.getStorageMethod())
                .foodStatus(true)
                .user(user)
                .food(food)
                .build();

        foodRegisterRepository.save(register);

        return new FoodRegisterResponse(register.getFoodRegisterId(), food.getFoodName(), expirationDate);
    }

    private LocalDate getLocalDate(FoodRegisterRequest request, LocalDate purchaseDate) {
        LocalDate expirationDate = Optional.ofNullable(request.getExpirationDate())
                .orElseGet(() -> expirationDateService.calculateExpiration(
                        request.getFoodCategory(), request.getStorageMethod(), purchaseDate
                ));
        return expirationDate;
    }
}
