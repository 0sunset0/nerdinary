package nerdinary.hackathon.domain.food.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import nerdinary.hackathon.domain.food.dto.FoodRegisterRequest;
import nerdinary.hackathon.domain.food.dto.FoodRegisterResponse;
import nerdinary.hackathon.domain.food.entity.Food;
import nerdinary.hackathon.domain.food.entity.FoodRegister;
import nerdinary.hackathon.domain.food.repository.FoodRegisterRepository;
import nerdinary.hackathon.domain.food.repository.FoodRepository;
import nerdinary.hackathon.global.exception.CustomException;
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
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Food food = foodRepository.findByFoodName(request.getFoodName())
                .orElseGet(() -> foodRepository.save(
                        new Food(request.getFoodName(), request.getFoodCategory())
                ));

        LocalDate purchaseDate = Optional.ofNullable(request.getPurchaseDate())
                .orElse(LocalDate.now());

        LocalDate expirationDate = Optional.ofNullable(request.getExpirationDate())
                .orElseGet(() -> expirationDateService.calculateExpiration(
                        request.getFoodName(), request.getStorageMethod(), purchaseDate
                ));

        FoodRegister foodRegister = FoodRegister.builder()
                .purchaseDate(purchaseDate)
                .expirationDate(expirationDate)
                .storageMethod(request.getStorageMethod())
                .foodStatus(true)
                .user(user)
                .food(food)
                .build();

        foodRegisterRepository.save(foodRegister);

        return new FoodRegisterResponse(foodRegister.getFoodRegisterId(), food.getFoodName(), expirationDate);
    }
}
