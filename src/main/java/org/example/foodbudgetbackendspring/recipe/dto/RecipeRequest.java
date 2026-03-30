package org.example.foodbudgetbackendspring.recipe.dto;

import org.example.foodbudgetbackendspring.recipe.model.Ingredient;

import java.util.List;

public record RecipeRequest(
        String name,
        String description,
        List<Ingredient> ingredients
) {
}
