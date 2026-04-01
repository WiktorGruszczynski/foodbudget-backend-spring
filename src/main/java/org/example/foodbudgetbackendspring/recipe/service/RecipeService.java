package org.example.foodbudgetbackendspring.recipe.service;

import lombok.RequiredArgsConstructor;
import org.example.foodbudgetbackendspring.product.model.MeasurementUnit;
import org.example.foodbudgetbackendspring.product.model.Product;
import org.example.foodbudgetbackendspring.recipe.dto.RecipePathRequest;
import org.example.foodbudgetbackendspring.recipe.dto.RecipeRequest;
import org.example.foodbudgetbackendspring.recipe.dto.RecipeResponse;
import org.example.foodbudgetbackendspring.recipe.mapper.RecipeMapper;
import org.example.foodbudgetbackendspring.recipe.model.Ingredient;
import org.example.foodbudgetbackendspring.recipe.model.Recipe;
import org.example.foodbudgetbackendspring.recipe.repository.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;

    private void createOrUpdateProductFromRecipe(Recipe recipe) {
        Product product = recipe.getProduct();

        if (product == null) {
            product = new Product();
            product.setOwner(recipe.getOwner());
            recipe.setProduct(product);
        }

        product.setName(recipe.getName());
        product.resetQuantity();
        product.resetNutrients();

        for (Ingredient ingredient : recipe.getIngredients()) {
            product.addNutrientsFrom(
                    ingredient.getProduct(),
                    ingredient.getQuantity()
            );
        }

        float totalWeight = product.getQuantity();

        if (totalWeight > 0) {
            product.applyScaling(100f / totalWeight);
        }
    }


    @Transactional // TODO - optimize amount of inserts in the future
    public RecipeResponse addRecipe(RecipeRequest request) {
        Recipe recipe = recipeRepository.save(
                recipeMapper.toEntity(request)
        );

        // This db call is needed in order to retrieve all related objects
        // Additionally, it does only one 'SELECT' call
        Recipe fullRecipe = recipeRepository
                .findByIdWithIngredientsAndProducts(recipe.getId())
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        createOrUpdateProductFromRecipe(fullRecipe);

        return recipeMapper.toResponse(fullRecipe);
    }

    @Transactional
    public RecipeResponse updateRecipe(Long id, RecipePathRequest request) {
        Recipe recipe = recipeRepository.findByIdWithIngredientsAndProducts(id).orElseThrow(
                () -> new RuntimeException("Recipe not found")
        );

        recipeMapper.patchRecipe(request, recipe);
        createOrUpdateProductFromRecipe(recipe);

        return recipeMapper.toResponse(
                recipeRepository.save(recipe)
        );
    }

    @Transactional(readOnly = true)
    public RecipeResponse getRecipe(Long id) {
        return recipeMapper.toResponse(
                recipeRepository
                        .findByIdWithIngredientsAndProducts(id)
                        .orElseThrow(
                                () -> new RuntimeException("Recipe not found")
                        )
        );
    }
}
