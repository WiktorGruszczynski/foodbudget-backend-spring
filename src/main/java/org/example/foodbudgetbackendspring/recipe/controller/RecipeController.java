package org.example.foodbudgetbackendspring.recipe.controller;

import lombok.RequiredArgsConstructor;
import org.example.foodbudgetbackendspring.recipe.dto.RecipeRequest;
import org.example.foodbudgetbackendspring.recipe.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;

    @PostMapping
    public ResponseEntity<?> addRecipe(@RequestBody RecipeRequest request){
        return recipeService.addRecipe(request);
    }
}
