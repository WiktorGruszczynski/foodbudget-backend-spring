package org.example.foodbudgetbackendspring.product.dto;

import org.example.foodbudgetbackendspring.product.model.MeasurementUnit;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponse(
        Long id,
        String name,
        String ean,
        String manufacturer,
        Float quantity,
        MeasurementUnit quantityUnit,
        MeasurementUnit nutrientUnit,
        Float density,
        Float energyKcal,
        Float fat,
        Float saturatedFat,
        Float carbohydrates,
        Float sugars,
        Float fiber,
        Float protein,
        Float salt,
        BigDecimal price,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}