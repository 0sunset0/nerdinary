package nerdinary.hackathon.domain.rate.dto;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class RateResponse {
    private String consumptionRate;           // 예: "90%"
    private String fridgeComment;             // 예: "냉꼼이 뿌듯! 완벽한 냉장고 관리에요!"
    private long nearExpiredCount;             // 예: 3
    private int level;                        // 예: 5
    private String typeName;                  // 예: "냉장고 천재형"
    private String foodBTI;                   // 예: "EFFS"
    private String foodBTIDetail;             // 예: "Eco-Friendly Fridge Saver"
    private String description;               // 예: 상세설명
}
