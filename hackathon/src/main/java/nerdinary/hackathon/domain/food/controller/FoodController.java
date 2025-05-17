package nerdinary.hackathon.domain.food.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nerdinary.hackathon.domain.food.dto.FoodRegisterRequest;
import nerdinary.hackathon.domain.food.dto.FoodRegisterResponse;
import nerdinary.hackathon.domain.food.service.FoodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/foods")
public class FoodController {

    private final FoodService foodService;

    @PostMapping("/register")
    public ResponseEntity<FoodRegisterResponse> registerFood(
            HttpServletRequest request,
            @RequestBody @Valid FoodRegisterRequest dto
    ) {
        Long userId = (Long) request.getAttribute("userId");
        FoodRegisterResponse response = foodService.registerFood(dto, userId);
        return ResponseEntity.ok(response);
    }
}