package nerdinary.hackathon.domain.food.repository;


import nerdinary.hackathon.domain.food.entity.FoodRegister;
import nerdinary.hackathon.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRegisterRepository extends JpaRepository<FoodRegister, Long> {

    // 특정 사용자 등록 음식 전체 조회
    List<FoodRegister> findByUser(User user);

    // 유통기한이 없는 음식 조회
    List<FoodRegister> findByUserAndExpirationDateIsNull(User user);

    // 유통기한이 지난 음식 조회
    List<FoodRegister> findByUserAndExpirationDateBeforeAndFoodStatusIsTrue(User user, java.time.LocalDate now);
}
