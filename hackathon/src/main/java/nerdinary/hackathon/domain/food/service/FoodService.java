package nerdinary.hackathon.domain.food.service;

import nerdinary.hackathon.domain.food.dto.AllFoodListResponse;
import nerdinary.hackathon.domain.food.dto.FoodRegisterRequest;
import nerdinary.hackathon.domain.food.dto.FoodRegisterResponse;
import nerdinary.hackathon.domain.food.dto.FoodSearchResponse;

import java.util.List;

public interface FoodService {

    FoodRegisterResponse registerFood(FoodRegisterRequest request, Long userId);
    List<AllFoodListResponse> getAllFoodsWithDday(Long userId);

    FoodSearchResponse searchFood(Long userId, String query);

    void consumeFood(Long foodRegisterId);
}
