package nerdinary.hackathon.domain.food.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "FOODS")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foodId;

    private String foodName;

    private String foodCategory;

    public Food(String foodName, String foodCategory) {
        this.foodName = foodName;
        this.foodCategory = foodCategory;
    }

}

