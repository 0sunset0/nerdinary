package nerdinary.hackathon.domain.food.repository;
import nerdinary.hackathon.domain.food.entity.Food;

import java.util.Optional;

public interface FoodRepositoryCustom {
    Optional<Food> findByNameUsingQueryDSL(String foodName);
}
