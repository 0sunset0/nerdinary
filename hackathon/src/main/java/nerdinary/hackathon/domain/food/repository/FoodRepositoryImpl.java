package nerdinary.hackathon.domain.food.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import nerdinary.hackathon.domain.food.entity.Food;

import java.util.Optional;

@RequiredArgsConstructor
public class FoodRepositoryImpl implements FoodRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Food> findByNameUsingQueryDSL(String foodName) {
        QFood food = QFood.food;

        Food result = queryFactory
                .selectFrom(food)
                .where(food.foodName.eq(foodName))
                .fetchOne();

        return Optional.ofNullable(result);
    }
}

