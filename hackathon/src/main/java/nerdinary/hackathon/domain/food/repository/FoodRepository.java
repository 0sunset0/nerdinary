package nerdinary.hackathon.domain.food.repository;


import nerdinary.hackathon.domain.food.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long>, FoodRepositoryCustom {
}
