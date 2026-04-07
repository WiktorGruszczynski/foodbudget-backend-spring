package org.example.foodbudgetbackendspring.recipe.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.example.foodbudgetbackendspring.product.model.MeasurementUnit;

import java.util.UUID;

public record IngredientRequest(
        @NotNull UUID productId,
        @PositiveOrZero Float quantity,
        @NotNull MeasurementUnit unit
) {
}
