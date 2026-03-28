package org.example.foodbudgetbackendspring.product.service;

import jakarta.validation.ValidationException;
import org.example.foodbudgetbackendspring.product.model.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductValidationService {
    private boolean isValidEANChecksum(String ean){
        int sum = 0;
        int length = ean.length();
        int checksumDigit = Character.getNumericValue(ean.charAt(length - 1));

        for (int i = 0; i < length-1; i++) {
            int digit = Character.getNumericValue(ean.charAt(i));

            int weight = (length == 13)
                    ? (i % 2 == 0 ? 1 : 3)
                    : (i % 2 == 0 ? 3 : 1);

            sum += digit * weight;
        }

        int calculatedChecksum = (10 - (sum % 10)) % 10;

        return checksumDigit == calculatedChecksum;
    }

    private void validateEAN(Product product){
        String ean = product.getEan();

        if (ean == null) return;

        if (!ean.matches("^(\\d{8}|\\d{13})$")){
            throw new ValidationException("Invalid EAN");
        }

        if (!isValidEANChecksum(ean)){
            throw new ValidationException("Invalid EAN");
        }
    }

    private void validateDensity(Product product){
        if (!product.isNutrientUnitLiquid() && product.getDensity() != null){
            throw new ValidationException("Nutrient unit is a mass unit, density is prohibited in this case");
        }
    }

    private void validateFatContent(Product product){
        if (product.getSaturatedFat() > product.getFat()){
            throw new ValidationException("Saturated fat cannot be greater than fat");
        }
    }

    private void validateCarbohydratesContent(Product product){
        if (product.getSugars() > product.getCarbohydrates()){
            throw new ValidationException("Sugars cannot be greater than carbohydrates");
        }
    }

    public Product validate(Product product) {
        validateEAN(product);
        validateDensity(product);
        validateFatContent(product);
        validateCarbohydratesContent(product);

        return product;
    }
}
