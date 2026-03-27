package org.example.foodbudgetbackendspring.product.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MeasurementUnit {
    GRAM("g"),
    MILLILITER("ml");

    private final String symbol;

    @JsonValue
    public String getSymbol() {
        return symbol;
    }
}
