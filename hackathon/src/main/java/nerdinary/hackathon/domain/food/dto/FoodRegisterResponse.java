package nerdinary.hackathon.domain.food.dto;

import java.time.LocalDate;

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