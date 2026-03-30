package org.example.foodbudgetbackendspring.recipe.service;

import lombok.RequiredArgsConstructor;
import org.example.foodbudgetbackendspring.recipe.dto.RecipeRequest;
import org.example.foodbudgetbackendspring.recipe.model.Recipe;
import org.example.foodbudgetbackendspring.recipe.repository.RecipeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public ResponseEntity<?> addRecipe(RecipeRequest request) {
        Recipe recipe = new Recipe();

        recipeRepository.save(recipe);
        return null;
    }
}
