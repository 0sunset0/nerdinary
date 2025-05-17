package nerdinary.hackathon.domain.food.repository;


import nerdinary.hackathon.domain.food.entity.FoodRegister;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRegisterRepository extends JpaRepository<FoodRegister, Long>, FoodRegisterRepositoryCustom {
}