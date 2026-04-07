package org.example.foodbudgetbackendspring.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.example.foodbudgetbackendspring.product.model.MeasurementUnit;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String name,
        String ean,
        String manufacturer,
        Float quantity,
        MeasurementUnit quantityUnit,
        MeasurementUnit nutrientUnit,
        Float energyKcal,
        Float fat,
        Float saturatedFat,
        Float carbohydrates,
        Float sugars,
        Float fiber,
        Float protein,
        Float salt,
        BigDecimal price,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime updatedAt
) {
    public ProductResponse {
        // Zaokrąglanie wszystkich wartości odżywczych i ilości
        quantity = round(quantity);
        energyKcal = round(energyKcal);
        fat = round(fat);
        saturatedFat = round(saturatedFat);
        carbohydrates = round(carbohydrates);
        sugars = round(sugars);
        fiber = round(fiber);
        protein = round(protein);
        salt = round(salt);
        price = price == null ? null : price.setScale(2, RoundingMode.HALF_UP);
    }

    private Float round(Float value) {
        if (value == null) return null;
        // Używamy BigDecimal dla precyzyjnego zaokrąglenia i uniknięcia błędów float
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .floatValue();
    }
}