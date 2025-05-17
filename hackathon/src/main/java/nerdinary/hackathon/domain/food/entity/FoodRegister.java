package nerdinary.hackathon.domain.food.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nerdinary.hackathon.domain.user.User;

import java.time.LocalDate;

@Entity
@Table(name = "FOOD_REGISTER")
@Getter
@NoArgsConstructor
@AllArgsConstructor @Builder
public class FoodRegister {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foodRegisterId;

    private LocalDate purchaseDate;

    private LocalDate expirationDate;

    private String storageMethod; // "냉장", "냉동", "실온"

    @Builder.Default
    private String foodStatus = "보관"; // "보관", "사용"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id")
    private Food food;

    public void consume() {
        this.foodStatus = "사용";
    }

}
