package org.example.foodbudgetbackendspring.recipe.dto;

import org.example.foodbudgetbackendspring.product.dto.ProductResponse;
import org.example.foodbudgetbackendspring.product.model.MeasurementUnit;

import java.math.BigDecimal;
import java.util.UUID;

public record IngredientResponse(
        UUID id,
        ProductResponse product,
        Float quantity,
        MeasurementUnit unit,
        BigDecimal price
) {
}
