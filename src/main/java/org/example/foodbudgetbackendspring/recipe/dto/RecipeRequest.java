package org.example.foodbudgetbackendspring.recipe.dto;



import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RecipeRequest(
        @NotNull String name,
        @NotNull String description,
        @Valid @NotEmpty List<IngredientRequest> ingredients
) {
}
