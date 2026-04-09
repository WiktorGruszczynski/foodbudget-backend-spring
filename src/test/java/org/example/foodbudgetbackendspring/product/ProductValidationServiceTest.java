package org.example.foodbudgetbackendspring.product;

import jakarta.validation.ValidationException;
import org.example.foodbudgetbackendspring.product.model.MeasurementUnit;
import org.example.foodbudgetbackendspring.product.model.Product;
import org.example.foodbudgetbackendspring.utils.TestDataFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductValidationServiceTest {
    private final ProductValidationService service = new ProductValidationService();

    @Test
    void shouldThrowExceptionWhenSugarGreaterThanCarbohydrates(){
        Product product = TestDataFactory.createTestProduct();
        product.setCarbohydrates(10f);
        product.setSugars(15f);

        assertThrows(ValidationException.class, () -> service.validate(product));
    }

    @Test
    void shouldThrowExceptionWhenSaturatedFatGreaterThanFat() {
        Product product = TestDataFactory.createTestProduct();
        product.setFat(5f);
        product.setSaturatedFat(7f);

        assertThrows(ValidationException.class, () -> service.validate(product));
    }

    @Test
    void shouldThrowExceptionWhenDensityProvidedForMassUnit() {
        Product product = TestDataFactory.createTestProduct();
        product.setNutrientUnit(MeasurementUnit.GRAM);
        product.setDensity(1.05f);

        assertThrows(ValidationException.class, () -> service.validate(product));
    }

    @Test
    void shouldThrowExceptionWhenNullDensityForLiquidProduct(){
        Product product = TestDataFactory.createTestProduct();
        product.setNutrientUnit(MeasurementUnit.MILLILITER);
        product.setDensity(null);

        assertThrows(ValidationException.class, () -> service.validate(product));
    }

    @Test
    void shouldThrowExceptionForInvalidEAN(){
        Product product = TestDataFactory.createTestProduct();
        product.setEan("5000112677868");

        assertThrows(ValidationException.class, () -> service.validate(product));
    }

    @Test
    void shouldAcceptValidEAN(){
        Product product = TestDataFactory.createTestProduct();
        product.setEan("5000112677867");

        service.validate(product);
    }
}