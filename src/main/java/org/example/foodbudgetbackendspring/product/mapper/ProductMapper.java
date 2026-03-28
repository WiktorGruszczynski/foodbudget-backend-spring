package org.example.foodbudgetbackendspring.product.mapper;

import org.example.foodbudgetbackendspring.product.dto.ProductPatchRequest;
import org.example.foodbudgetbackendspring.product.dto.ProductRequest;
import org.example.foodbudgetbackendspring.product.dto.ProductResponse;
import org.example.foodbudgetbackendspring.product.model.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapper {
    public Product toEntity(ProductRequest request) {
        Product product = new Product();
        product.setName(request.name());
        product.setEan(request.ean());
        product.setManufacturer(request.manufacturer());
        product.setQuantity(request.quantity());
        product.setQuantityUnit(request.quantityUnit());
        product.setNutrientUnit(request.nutrientUnit());
        product.setDensity(request.density());
        product.setEnergyKcal(request.energyKcal());
        product.setFat(request.fat());
        product.setSaturatedFat(request.saturatedFat());
        product.setCarbohydrates(request.carbohydrates());
        product.setSugars(request.sugars());
        product.setFiber(request.fiber());
        product.setProtein(request.protein());
        product.setSalt(request.salt());
        product.setPrice(request.price());
        return product;
    }

    public void patch(Product product, ProductPatchRequest request) {
        if (request.name() != null) product.setName(request.name());
        if (request.ean() != null) product.setEan(request.ean());
        if (request.manufacturer() != null) product.setManufacturer(request.manufacturer());
        if (request.quantity() != null) product.setQuantity(request.quantity());
        if (request.density() != null) product.setDensity(request.density());
        if (request.energyKcal() != null) product.setEnergyKcal(request.energyKcal());
        if (request.fat() != null) product.setFat(request.fat());
        if (request.saturatedFat() != null) product.setSaturatedFat(request.saturatedFat());
        if (request.carbohydrates() != null) product.setCarbohydrates(request.carbohydrates());
        if (request.sugars() != null) product.setSugars(request.sugars());
        if (request.fiber() != null) product.setFiber(request.fiber());
        if (request.protein() != null) product.setProtein(request.protein());
        if (request.salt() != null) product.setSalt(request.salt());
        if (request.price() != null) product.setPrice(request.price());
    }

    public ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getEan(),
                product.getManufacturer(),
                product.getQuantity(),
                product.getQuantityUnit(),
                product.getNutrientUnit(),
                product.getDensity(),
                product.getEnergyKcal(),
                product.getFat(),
                product.getSaturatedFat(),
                product.getCarbohydrates(),
                product.getSugars(),
                product.getFiber(),
                product.getProtein(),
                product.getSalt(),
                product.getPrice(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }

    public List<ProductResponse> toResponseList(List<Product> products) {
        List<ProductResponse> list = new ArrayList<>();

        for (Product product: products) {
            list.add(toResponse(product));
        }

        return list;
    }
}
