package nerdinary.hackathon.domain.food.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class FoodRegisterResponse {
    private Long foodRegisterId;
    private String foodName;
    private LocalDate expirationDate;

    public FoodRegisterResponse(Long id, String name, LocalDate expirationDate) {
        this.foodRegisterId = id;
        this.foodName = name;
        this.expirationDate = expirationDate;
    }
}