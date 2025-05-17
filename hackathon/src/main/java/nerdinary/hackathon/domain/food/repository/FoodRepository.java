package nerdinary.hackathon.domain.food.repository;

import nerdinary.hackathon.domain.food.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long> {

    // food_name 기준 조회 (중복 방지 목적)
    Optional<Food> findByFoodName(String foodName);
}
