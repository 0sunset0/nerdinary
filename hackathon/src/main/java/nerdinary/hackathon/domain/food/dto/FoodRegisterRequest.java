package nerdinary.hackathon.domain.food.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class FoodRegisterRequest {

    private String foodName;
    private String foodCategory;
    private LocalDate purchaseDate;
    private LocalDate expirationDate;
    private String storageMethod;
}
