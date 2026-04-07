package org.example.foodbudgetbackendspring.recipe.dto;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.PositiveOrZero;
import org.example.foodbudgetbackendspring.product.model.MeasurementUnit;

import java.util.UUID;

public record IngredientRequest(
        @Nonnull UUID productId,
        @PositiveOrZero Float quantity,
        @Nonnull MeasurementUnit unit
) {
}
