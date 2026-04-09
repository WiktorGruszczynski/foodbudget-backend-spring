package org.example.foodbudgetbackendspring.recipe;

import lombok.RequiredArgsConstructor;
import org.example.foodbudgetbackendspring.product.ProductService;
import org.example.foodbudgetbackendspring.recipe.dto.*;
import org.example.foodbudgetbackendspring.recipe.model.Ingredient;
import org.example.foodbudgetbackendspring.recipe.model.Recipe;
import org.example.foodbudgetbackendspring.user.model.User;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
class RecipeMapper {
    private final ProductService productService;
    private final IngredientValidationService ingredientValidationService;

    private Ingredient toEntity(IngredientRequest ingRequest){
        Ingredient ingredient = new Ingredient();
        ingredient.setQuantity(ingRequest.quantity());
        ingredient.setUnit(ingRequest.unit());
        ingredient.setProduct(
                productService.getProductReference(ingRequest.productId())
        );

        return ingredient;
    }

    private IngredientResponse toResponse(Ingredient ingredient){
        return new IngredientResponse(
                ingredient.getId(),
                productService.mapToResponse(
                        ingredient.getProduct()
                ),
                ingredient.getQuantity(),
                ingredient.getUnit(),
                ingredient.getPrice()
        );
    }

    public Recipe toEntity(RecipeRequest request, User user) {
        Recipe recipe = new Recipe();
        recipe.setName(request.name());
        recipe.setDescription(request.description());
        recipe.setOwner(user);

        for (IngredientRequest ingredientRequest : request.ingredients()) {
            recipe.addIngredient(
                    ingredientValidationService.validate(
                            toEntity(ingredientRequest)
                    )
            );
        }

        return recipe;
    }

    public RecipeResponse toResponse(Recipe recipe) {
        return new RecipeResponse(
                recipe.getId(),
                recipe.getName(),
                recipe.getDescription(),
                recipe.getIngredients().stream().map(this::toResponse).toList()
        );
    }

    public void patchRecipe(RecipePathRequest request, Recipe recipe) {
        if (request.name() != null) recipe.setName(request.name());
        if (request.description() != null) recipe.setDescription(request.description());

        recipe.getIngredients().clear();

        for (IngredientRequest ingredientRequest : request.ingredients()) {
            recipe.addIngredient(
                    ingredientValidationService.validate(
                            toEntity(ingredientRequest)
                    )
            );
        }
    }
}
