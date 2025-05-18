package nerdinary.hackathon.domain.food.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nerdinary.hackathon.domain.food.dto.AllFoodListResponse;
import nerdinary.hackathon.domain.food.dto.FoodRegisterRequest;
import nerdinary.hackathon.domain.food.dto.FoodRegisterResponse;
import nerdinary.hackathon.domain.food.dto.FoodSearchResponse;
import nerdinary.hackathon.domain.food.service.FoodService;
import nerdinary.hackathon.domain.login.jwt.JwtValidation;
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
            parameters = {
                    @Parameter(name = "Authorization", in = ParameterIn.HEADER, required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "등록 성공",
                            content = @Content(schema = @Schema(implementation = FoodRegisterResponse.class))),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            }
    )
    @PostMapping("/register")
    public ResponseEntity<FoodRegisterResponse> registerFood(
            @Parameter(hidden = true) @JwtValidation Long userId,
            @Parameter(description = "음식 등록 정보", required = true, schema = @Schema(implementation = FoodRegisterRequest.class))
            @RequestBody @Valid FoodRegisterRequest dto
    ) {
        FoodRegisterResponse response = foodService.registerFood(dto, userId);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "전체 음식 목록 조회",
            description = "모든 등록 음식의 이름, 소비기한, D-day를 조회합니다. 보관 방식 및 D-7 임박 여부로 필터링 가능",
            parameters = {
                    @Parameter(name = "Authorization", in = ParameterIn.HEADER, required = true,
                            description = "JWT 액세스 토큰"),
                    @Parameter(name = "storage", in = ParameterIn.QUERY, required = false,
                            description = "보관 방식 (예: 냉장, 냉동, 실온)"),
                    @Parameter(name = "isExpiringSoon", in = ParameterIn.QUERY, required = false,
                            description = "true이면 소비기한 7일 이하만 필터링")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "조회 성공",
                            content = @Content(schema = @Schema(implementation = AllFoodListResponse.class)))
            }
    )
    @GetMapping
    public ResponseEntity<List<AllFoodListResponse>> getAllFoods(
            @Parameter(hidden = true) @JwtValidation Long userId,
            @RequestParam(required = false) String storage,
            @RequestParam(required = false, defaultValue = "false") boolean isExpiringSoon
    ) {
        List<AllFoodListResponse> foods = foodService.getFilteredFoods(userId, storage, isExpiringSoon);
        return ResponseEntity.ok(foods);
    }


    @Operation(
        summary = "음식 검색",
        description = "음식이름을 검색하면 자신이 등록한 음식들을 반환합니다.",
        parameters = {
            @Parameter(name = "Authorization", in = ParameterIn.HEADER, required = true,
                description = "JWT 액세스 토큰"),
            @Parameter(name = "query", description = "검색어", required = true, in = ParameterIn.QUERY)
        },
        responses = {
            @ApiResponse(responseCode = "200", description = "검색 성공",
                content = @Content(schema = @Schema(implementation = FoodSearchResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
        }
    )
    @GetMapping("/search")
    public ResponseEntity<FoodSearchResponse> searchFood(
            @Parameter(hidden = true) @JwtValidation Long userId,
            @RequestParam String query
    ) {
        FoodSearchResponse response = foodService.searchFood(userId, query);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "음식 소비(사용)",
        description = "등록된 음식을 소비 상태로 변경합니다.",
        parameters = {
            @Parameter(name = "Authorization", in = ParameterIn.HEADER, required = true,
                description = "JWT 액세스 토큰"),
            @Parameter(name = "foodRegisterId", required = true, in = ParameterIn.QUERY,
                description = "소비할 음식의 등록 ID")
        },
        responses = {
            @ApiResponse(responseCode = "200", description = "소비 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
        }
    )
    @PutMapping("/consume")
    public ResponseEntity<Void> consumeFood(
            @Parameter(hidden = true) @JwtValidation Long userId,
            @Parameter(name = "foodRegisterId", required = true, in = ParameterIn.QUERY)
            @RequestParam Long foodRegisterId
    ) {
        foodService.consumeFood(userId, foodRegisterId);
        return ResponseEntity.ok().build();
    }


}