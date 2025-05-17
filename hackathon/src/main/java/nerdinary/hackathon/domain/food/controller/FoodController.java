package nerdinary.hackathon.domain.food.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nerdinary.hackathon.domain.food.dto.AllFoodListResponse;
import nerdinary.hackathon.domain.food.dto.FoodRegisterRequest;
import nerdinary.hackathon.domain.food.dto.FoodRegisterResponse;
import nerdinary.hackathon.domain.food.service.FoodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/foods")
@Tag(name = "Food", description = "음식 API")
public class FoodController {

    private final FoodService foodService;

    @Operation(
            summary = "음식 등록",
            description = "사용자가 음식을 등록합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "등록 성공",
                            content = @Content(schema = @Schema(implementation = FoodRegisterResponse.class))),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            }
    )
    @PostMapping("/register")
    public ResponseEntity<FoodRegisterResponse> registerFood(
            HttpServletRequest request,
            @Parameter(description = "음식 등록 정보", required = true, schema = @Schema(implementation = FoodRegisterRequest.class))
            @RequestBody @Valid FoodRegisterRequest dto
    ) {
        Long userId = (Long) request.getAttribute("userId");
        FoodRegisterResponse response = foodService.registerFood(dto, userId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "전체 음식 목록 조회", description = "모든 등록 음식의 이름, 소비기한, D-day를 조회합니다.")
    @GetMapping
    public ResponseEntity<List<AllFoodListResponse>> getAllFoods(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        List<AllFoodListResponse> foods = foodService.getAllFoodsWithDday(userId);
        return ResponseEntity.ok(foods);
    }

}