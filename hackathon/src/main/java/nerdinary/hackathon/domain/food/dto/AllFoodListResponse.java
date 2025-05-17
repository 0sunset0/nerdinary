package nerdinary.hackathon.domain.food.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class AllFoodListResponse {
    private long foodRegisterId;
    private String foodName;
    private String foodCategory;
    private LocalDate expirationDate;
    private long daysLeft;
}
