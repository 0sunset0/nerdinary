package nerdinary.hackathon.domain.food.service;

import nerdinary.hackathon.domain.food.dto.AllFoodListResponse;
import nerdinary.hackathon.domain.food.dto.FoodRegisterRequest;
import nerdinary.hackathon.domain.food.dto.FoodRegisterResponse;
import nerdinary.hackathon.domain.food.dto.FoodSearchResponse;

import java.util.List;

public interface FoodService {

    FoodRegisterResponse registerFood(FoodRegisterRequest request, Long userId);
    //List<AllFoodListResponse> getAllFoodsWithDday(Long userId); 필터링 기능 추가해서 getFilteredFoods로 대체
    FoodSearchResponse searchFood(Long userId, String query);

    void consumeFood(Long foodRegisterId);
    List<AllFoodListResponse> getFilteredFoods(Long userId, String storageMethod, boolean isExpiringSoon);

}
