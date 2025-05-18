package nerdinary.hackathon.domain.rate.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import nerdinary.hackathon.domain.login.jwt.JwtValidation;
import nerdinary.hackathon.domain.rate.dto.RateResponse;
import nerdinary.hackathon.domain.rate.rateService.RateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Rate", description = "사용자의 냉장고 관리율 및 BTI 통계 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/foods")
public class RateController {

    private final RateService rateService;

    @Operation(
        summary = "MBTI 분석 결과 조회",
        description = "사용자의 냉장고 소비율, 유통기한 임박 식품 개수, 레벨, BTI 정보 등을 조회합니다.",
        parameters = {
            @Parameter(name = "Authorization", in = ParameterIn.HEADER, required = true,
                description = "JWT 액세스 토큰") // 헤더로 JWT 토큰을 받음
        }
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "정상적으로 분석 결과가 반환됨"),
        @ApiResponse(responseCode = "401", description = "JWT 인증 실패"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/rate")
    public ResponseEntity<RateResponse> getRate(
            @Parameter(hidden = true)
            @JwtValidation Long userId) {
        RateResponse response = rateService.calculateUserRate(userId);
        return ResponseEntity.ok(response);
    }
}