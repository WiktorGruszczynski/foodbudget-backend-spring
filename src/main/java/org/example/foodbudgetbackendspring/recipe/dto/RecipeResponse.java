package org.example.foodbudgetbackendspring.recipe.dto;

import java.util.List;
import java.util.UUID;

public record RecipeResponse(
        UUID id,
        String name,
        String description,
        List<IngredientResponse> ingredients
) {
}
