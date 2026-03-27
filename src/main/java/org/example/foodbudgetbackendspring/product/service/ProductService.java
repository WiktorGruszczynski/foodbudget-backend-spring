package org.example.foodbudgetbackendspring.product.service;

import lombok.RequiredArgsConstructor;
import org.example.foodbudgetbackendspring.product.dto.ProductPatchRequest;
import org.example.foodbudgetbackendspring.product.dto.ProductRequest;
import org.example.foodbudgetbackendspring.product.dto.ProductResponse;
import org.example.foodbudgetbackendspring.product.mapper.ProductMapper;
import org.example.foodbudgetbackendspring.product.model.MeasurementUnit;
import org.example.foodbudgetbackendspring.product.model.Product;
import org.example.foodbudgetbackendspring.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final ProductDensityService productDensityService;

    private boolean isProductNutrientUnitLiquid(Product product) {
        return product.getNutrientUnit().equals(MeasurementUnit.MILLILITER);
    }

    public ProductResponse addProduct(ProductRequest request) {
        Product product = productMapper.toEntity(request);
        if (isProductNutrientUnitLiquid(product)) {
            product.setDensity(
                    productDensityService.getDensityByProductName(product.getName())
            );
        }

        return productMapper.toResponse(productRepository.save(product));
    }

    public ProductResponse updateProduct(Long id, ProductPatchRequest request) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        productMapper.patch(product, request);

        return productMapper.toResponse(
                productRepository.save(product)
        );
    }

    public ProductResponse getProductById(Long id) {
        return productMapper.toResponse(
                productRepository.findById(id).orElseThrow(
                        () -> new RuntimeException("Product with id " + id + " not found")
                )
        );
    }

   public List<ProductResponse> getProducts(String query) {
        return productMapper.toResponseList(
                productRepository.findByNameContainingIgnoreCase(query)
        );
   }
}
