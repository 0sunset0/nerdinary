package nerdinary.hackathon.domain.food.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class FoodSearchResponse {
    private List<FoodSearchResultDto> results;
}
