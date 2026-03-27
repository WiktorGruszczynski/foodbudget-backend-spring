package org.example.foodbudgetbackendspring.product.dto;

import jakarta.validation.constraints.*;
import org.example.foodbudgetbackendspring.product.model.MeasurementUnit;

import java.math.BigDecimal;

public record ProductPatchRequest(
        String name,
        String ean,
        String manufacturer,

        @Positive Float quantity,
        MeasurementUnit quantityUnit,
        MeasurementUnit nutrientUnit,

        @Positive Float density,
        @Positive Float energyKcal,

        @PositiveOrZero Float fat,
        @PositiveOrZero Float saturatedFat,
        @PositiveOrZero Float carbohydrates,
        @PositiveOrZero Float sugars,
        @PositiveOrZero Float fiber,
        @PositiveOrZero Float protein,
        @PositiveOrZero Float salt,

        @Positive @Digits(integer = 10, fraction = 2) BigDecimal price
)
{
}
