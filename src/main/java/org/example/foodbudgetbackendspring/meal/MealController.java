package org.example.foodbudgetbackendspring.meal;

import lombok.RequiredArgsConstructor;
import org.example.foodbudgetbackendspring.meal.dto.MealItemRequest;
import org.example.foodbudgetbackendspring.meal.dto.MealItemResponse;
import org.example.foodbudgetbackendspring.meal.dto.MealResponse;
import org.example.foodbudgetbackendspring.user.model.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/meal")
@RequiredArgsConstructor
public class MealController {
    private final MealService mealService;

    @GetMapping
    public ResponseEntity<List<MealResponse>> getUserMeals(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate
    ){
        return ResponseEntity.ok(
                mealService.getUserMealsFromRange(userDetails.getId(), startDate, endDate)
        );
    }

    @PostMapping
    public ResponseEntity<MealItemResponse> addMealItem(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody MealItemRequest request
    ){
        return ResponseEntity
                .status(201)
                .body(
                        mealService.addMealItem(userDetails.getId(), request)
                );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMealItem(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("id") UUID id
    ){
        mealService.removeMealItem(
                userDetails.getId(),
                id
        );
    }
}
