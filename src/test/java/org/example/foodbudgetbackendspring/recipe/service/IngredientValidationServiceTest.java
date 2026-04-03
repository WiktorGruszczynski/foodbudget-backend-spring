package org.example.foodbudgetbackendspring.recipe.service;

import jakarta.validation.ValidationException;
import org.example.foodbudgetbackendspring.product.model.MeasurementUnit;
import org.example.foodbudgetbackendspring.product.model.Product;
import org.example.foodbudgetbackendspring.recipe.model.Ingredient;
import org.example.foodbudgetbackendspring.utils.TestDataFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IngredientValidationServiceTest {
    private final IngredientValidationService service = new IngredientValidationService();

    @Test
    void shouldThrowExceptionWhenLiquidUnitPassedForSolidProduct() {
        Product product = TestDataFactory.createTestProduct();
        product.setQuantityUnit(MeasurementUnit.GRAM);
        product.setNutrientUnit(MeasurementUnit.GRAM);

        Ingredient ingredient = TestDataFactory.createTestIngredient(product);
        ingredient.setUnit(MeasurementUnit.MILLILITER);

        assertThrows(ValidationException.class, () -> service.validate(ingredient));
    }
}