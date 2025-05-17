package nerdinary.hackathon.domain.food.repository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import nerdinary.hackathon.domain.food.entity.FoodRegister;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class FoodRegisterRepositoryImpl implements FoodRegisterRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<FoodRegister> findAllByUser(User user) {
        QFoodRegister register = QFoodRegister.foodRegister;

        return queryFactory.selectFrom(register)
                .where(register.user.eq(user))
                .fetch();
    }

    @Override
    public List<FoodRegister> findAllWithoutExpirationDate(User user) {
        QFoodRegister register = QFoodRegister.foodRegister;

        return queryFactory.selectFrom(register)
                .where(register.user.eq(user),
                        register.expirationDate.isNull())
                .fetch();
    }

    @Override
    public List<FoodRegister> findExpiredFoods(User user, LocalDate nowDate) {
        QFoodRegister register = QFoodRegister.foodRegister;

        return queryFactory.selectFrom(register)
                .where(register.user.eq(user),
                        register.expirationDate.before(nowDate),
                        register.foodStatus.isTrue()) // 보관 중일 때만
                .fetch();
    }
}
