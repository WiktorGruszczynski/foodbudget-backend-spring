package org.example.foodbudgetbackendspring.recipe.dto;


import jakarta.annotation.Nonnull;

import java.util.List;

public record RecipeRequest(
        @Nonnull String name,
        @Nonnull String description,
        @Nonnull List<IngredientRequest> ingredients
) {
}
