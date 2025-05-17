package nerdinary.hackathon.domain.rate.controller;

import lombok.RequiredArgsConstructor;
import nerdinary.hackathon.domain.login.jwt.JwtValidation;
import nerdinary.hackathon.domain.rate.dto.RateResponse;
import nerdinary.hackathon.domain.rate.rateService.RateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/foods")
public class RateController {

    private final RateService rateService;

    @GetMapping("/rate")
    public ResponseEntity<RateResponse> getRate(@JwtValidation Long userId) {
        RateResponse response = rateService.calculateUserRate(userId);
        return ResponseEntity.ok(response);
    }
}