package org.example.foodbudgetbackendspring.meal.dto;

import org.example.foodbudgetbackendspring.product.dto.ProductResponse;
import org.example.foodbudgetbackendspring.product.model.MeasurementUnit;

import java.util.UUID;

public record MealItemResponse(
        UUID id,
        ProductResponse product,
        float quantity,
        MeasurementUnit unit
) {
}
