package org.example.foodbudgetbackendspring.recipe.controller;

import lombok.RequiredArgsConstructor;
import org.example.foodbudgetbackendspring.recipe.dto.RecipePathRequest;
import org.example.foodbudgetbackendspring.recipe.dto.RecipeRequest;
import org.example.foodbudgetbackendspring.recipe.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;

    @PostMapping
    public ResponseEntity<?> addRecipe(@RequestBody RecipeRequest request){
        return ResponseEntity
                .status(201)
                .body(
                        recipeService.addRecipe(request)
                );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateRecipe(@PathVariable("id") Long id, @RequestBody RecipePathRequest request){
        return ResponseEntity.ok(
                recipeService.updateRecipe(id, request)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRecipe(@PathVariable Long id){
        return ResponseEntity.ok(
                recipeService.getRecipe(id)
        );
    }
}
