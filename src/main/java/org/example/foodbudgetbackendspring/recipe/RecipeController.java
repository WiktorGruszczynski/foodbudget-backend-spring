package org.example.foodbudgetbackendspring.recipe;

import lombok.RequiredArgsConstructor;
import org.example.foodbudgetbackendspring.recipe.dto.RecipePathRequest;
import org.example.foodbudgetbackendspring.recipe.dto.RecipeRequest;
import org.example.foodbudgetbackendspring.user.model.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;

    @PostMapping
    public ResponseEntity<?> addRecipe(
            @RequestBody RecipeRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        return ResponseEntity
                .status(201)
                .body(
                        recipeService.addRecipe(request, userDetails)
                );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateRecipe(@PathVariable("id") UUID id, @RequestBody RecipePathRequest request){
        return ResponseEntity.ok(
                recipeService.updateRecipe(id, request)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRecipe(@PathVariable UUID id){
        return ResponseEntity.ok(
                recipeService.getRecipe(id)
        );
    }
}
