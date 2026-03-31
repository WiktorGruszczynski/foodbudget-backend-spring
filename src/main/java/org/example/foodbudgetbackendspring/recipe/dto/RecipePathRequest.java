package org.example.foodbudgetbackendspring.recipe.dto;


import java.util.List;

public record RecipePathRequest(
        String name,
        String description,
        List<IngredientRequest> ingredients
) {
}
