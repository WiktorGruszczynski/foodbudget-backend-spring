package org.example.foodbudgetbackendspring.recipe.mapper;

import org.example.foodbudgetbackendspring.recipe.dto.RecipeRequest;
import org.example.foodbudgetbackendspring.recipe.model.Recipe;
import org.springframework.stereotype.Component;

@Component
public class RecipeMapper {
    public Recipe toEntity(RecipeRequest request) {
        Recipe recipe = new Recipe();
        recipe.setName(request.name());
        recipe.setDescription(request.description());
        recipe.setIngredients(request.ingredients());

        return recipe;
    }
}
