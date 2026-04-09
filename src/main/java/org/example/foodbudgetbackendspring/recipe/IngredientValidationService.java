package org.example.foodbudgetbackendspring.recipe;

import jakarta.validation.ValidationException;
import org.example.foodbudgetbackendspring.product.model.MeasurementUnit;
import org.example.foodbudgetbackendspring.product.model.Product;
import org.example.foodbudgetbackendspring.recipe.model.Ingredient;
import org.springframework.stereotype.Service;


@Service
class IngredientValidationService {
    private void validateUnits(Ingredient ingredient) {
        Product product = ingredient.getProduct();
        MeasurementUnit unit = ingredient.getUnit();

        if (product.isSolid() && unit.equals(MeasurementUnit.MILLILITER)) {
            throw new ValidationException(
                    "Solid product " + product.getName() + " cannot be measured in ML"
            );
        }
    }

    public Ingredient validate(Ingredient ingredient) {
        validateUnits(ingredient);

        return ingredient;
    }
}
