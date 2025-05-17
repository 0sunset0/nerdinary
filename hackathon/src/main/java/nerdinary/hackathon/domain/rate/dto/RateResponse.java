package nerdinary.hackathon.domain.rate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class RateResponse {

    @Schema(description = "소비율 (예: 90%)")
    private String consumptionRate;

    @Schema(description = "냉장고 코멘트")
    private String fridgeComment;

    @Schema(description = "유통기한 임박 식품 개수")
    private long nearExpiredCount;

    @Schema(description = "사용자 레벨")
    private int level;

    @Schema(description = "냉장고 유형 이름")
    private String typeName;

    @Schema(description = "냉장고 BTI (예: EFFS)")
    private String foodBTI;

    @Schema(description = "냉장고 BTI 상세 명칭")
    private String foodBTIDetail;

    @Schema(description = "BTI 설명")
    private String description;
}