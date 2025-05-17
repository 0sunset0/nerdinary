package nerdinary.hackathon.domain.food.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class FoodSearchResultDto {
    private Long foodRegisterId;
    private String foodName;
    private LocalDate expirationDate;
    private Long remainingDays;
}

