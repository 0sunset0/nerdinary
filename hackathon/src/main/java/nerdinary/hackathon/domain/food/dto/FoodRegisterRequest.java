package nerdinary.hackathon.domain.food.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class FoodRegisterRequest {

    private String foodName;
    private String foodCategory;
    private LocalDate purchaseDate;
    private LocalDate expirationDate;
    private String storageMethod;
}
