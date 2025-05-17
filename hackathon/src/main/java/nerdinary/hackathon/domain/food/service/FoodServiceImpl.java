package nerdinary.hackathon.domain.food.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import nerdinary.hackathon.domain.food.dto.AllFoodListResponse;
import nerdinary.hackathon.domain.food.dto.FoodRegisterRequest;
import nerdinary.hackathon.domain.food.dto.FoodRegisterResponse;
import nerdinary.hackathon.domain.food.dto.FoodSearchResponse;
import nerdinary.hackathon.domain.food.dto.FoodSearchResultDto;
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
import java.time.temporal.ChronoUnit;
import java.util.List;
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
        LocalDate expirationDate = Optional.ofNullable(request.getExpirationDate())
                .orElseGet(() -> expirationDateService.calculateExpiration(
                        request.getFoodName(), request.getStorageMethod(), purchaseDate
                ));

        // 5. 음식 등록 저장
        FoodRegister register = FoodRegister.builder()
                .purchaseDate(purchaseDate)
                .expirationDate(expirationDate)
                .storageMethod(request.getStorageMethod())
                .foodStatus("보관")
                .user(user)
                .food(food)
                .build();

        foodRegisterRepository.save(register);

        register.getUser().plusTotalFoodCount();
        return new FoodRegisterResponse(register.getFoodRegisterId(), food.getFoodName(), expirationDate);
    }

    @Override
    @Transactional
    public FoodSearchResponse searchFood(Long userId, String query){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        List<FoodRegister> foodRegisters = foodRegisterRepository
                .findByUserAndFood_FoodNameContainingIgnoreCase(user, query);

        List<FoodSearchResultDto> results = foodRegisters.stream()
                .map(fr -> new FoodSearchResultDto(
                        fr.getFoodRegisterId(),
                        fr.getFood().getFoodName(),
                        fr.getExpirationDate(),
                        fr.getExpirationDate() != null && fr.getPurchaseDate() != null
                                ? (long) fr.getExpirationDate().toEpochDay() - fr.getPurchaseDate().toEpochDay()
                                : null
                ))
                .toList();

        return new FoodSearchResponse(results);
    }

    @Transactional
    @Override
    public void consumeFood(Long foodRegisterId) {
        FoodRegister foodRegister = foodRegisterRepository.findById(foodRegisterId)
                .orElseThrow(() -> new CustomException(ErrorCode.FOOD_REGISTER_NOT_FOUND));

        foodRegister.consume(); //상태 변경
        //유저 사용 개수 증가
        User user = foodRegister.getUser();
        user.plusUsedCount();
    }

    @Override
    @Transactional
    public List<AllFoodListResponse> getAllFoodsWithDday(Long userId) {
        // User 객체 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 사용자 기반 음식 등록 정보 조회
        List<FoodRegister> foodList = foodRegisterRepository.findByUser(user);

        // 응답 DTO 변환
        return foodList.stream()
                .map(foodRegister -> {
                    String name = foodRegister.getFood().getFoodName();
                    LocalDate expiration = foodRegister.getExpirationDate();
                    long daysLeft = expiration != null
                            ? ChronoUnit.DAYS.between(LocalDate.now(), expiration)
                            : -1;
                    return new AllFoodListResponse(name, expiration, daysLeft);
                })
                .toList();
    }

}
