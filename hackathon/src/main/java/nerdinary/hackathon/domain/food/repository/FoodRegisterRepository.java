package nerdinary.hackathon.domain.food.repository;


import nerdinary.hackathon.domain.food.entity.FoodRegister;
import nerdinary.hackathon.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface FoodRegisterRepository extends JpaRepository<FoodRegister, Long> {

    // 특정 사용자 등록 음식 전체 조회
    List<FoodRegister> findByUser(User user);

    // 유통기한이 없는 음식 조회
    List<FoodRegister> findByUserAndExpirationDateIsNull(User user);

    // 유통기한이 지난 음식 조회
    long countByUserAndExpirationDateBeforeAndFoodStatus(User user, LocalDate now, String foodStatus);

    // 특정사용자가 음식이름 조회
    List<FoodRegister> findByUserAndFood_FoodNameContainingIgnoreCase(User user, String foodName);

    // 특정 user의 foodStatus가 "사용"인 foodRegister 개수 반환
    long countByUserAndFoodStatus(User user, String foodStatus);

    // 특정 user가 가진 foodRegister 전체 개수 반환
    long countByUser(User user);
}
