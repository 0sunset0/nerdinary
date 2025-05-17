package nerdinary.hackathon.domain.food.service;

import nerdinary.hackathon.domain.food.dto.FoodRegisterRequest;
import nerdinary.hackathon.domain.food.dto.FoodRegisterResponse;
import nerdinary.hackathon.domain.food.dto.FoodSearchResponse;

public interface FoodService {

    FoodRegisterResponse registerFood(FoodRegisterRequest request, Long userId);
    FoodSearchResponse searchFood(Long userId, String query);
}
