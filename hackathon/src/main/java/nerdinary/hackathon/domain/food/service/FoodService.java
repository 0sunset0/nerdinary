package nerdinary.hackathon.domain.food.service;

import nerdinary.hackathon.domain.food.dto.FoodRegisterRequest;
import nerdinary.hackathon.domain.food.dto.FoodRegisterResponse;

public interface FoodService {

    FoodRegisterResponse registerFood(FoodRegisterRequest request, Long userId);
}
