package nerdinary.hackathon.domain.food.repository;

import nerdinary.hackathon.domain.food.entity.FoodRegister;

import java.time.LocalDate;
import java.util.List;

public interface FoodRegisterRepositoryCustom {

    List<FoodRegister> findAllByUser(User user);

    List<FoodRegister> findAllWithoutExpirationDate(User user);

    List<FoodRegister> findExpiredFoods(User user, LocalDate nowDate);
}
