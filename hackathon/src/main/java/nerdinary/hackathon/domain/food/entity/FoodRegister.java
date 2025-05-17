package nerdinary.hackathon.domain.food.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "FOOD_REGISTER")
@Getter
@NoArgsConstructor
public class FoodRegister {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foodRegisterId;

    private Integer quantity;

    private LocalDate purchaseDate;

    private LocalDate expirationDate;

    private String storageMethod; // "냉장", "냉동", "실온"

    private Boolean foodStatus = true; // true = 보관 중, false = 폐기됨

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id")
    private Food food;
}
