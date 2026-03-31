package org.example.foodbudgetbackendspring.recipe.dto;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.PositiveOrZero;
import org.example.foodbudgetbackendspring.product.model.MeasurementUnit;

public record IngredientRequest(
        @Nonnull Long productId,
        @PositiveOrZero Float quantity,
        @Nonnull MeasurementUnit unit
) {
}
