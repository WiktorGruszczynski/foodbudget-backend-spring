package org.example.foodbudgetbackendspring.product.service;

import lombok.RequiredArgsConstructor;
import org.example.foodbudgetbackendspring.product.dto.ProductPatchRequest;
import org.example.foodbudgetbackendspring.product.dto.ProductRequest;
import org.example.foodbudgetbackendspring.product.dto.ProductResponse;
import org.example.foodbudgetbackendspring.product.mapper.ProductMapper;
import org.example.foodbudgetbackendspring.product.model.Product;
import org.example.foodbudgetbackendspring.product.repository.ProductRepository;
import org.example.foodbudgetbackendspring.user.model.CustomUserDetails;
import org.example.foodbudgetbackendspring.user.repository.UserRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ProductDensityService productDensityService;
    private final ProductValidationService productValidationService;


    public ProductResponse addProduct(ProductRequest request, CustomUserDetails userDetails) {
        Product product = productMapper.toEntity(
                request,
                userRepository.getReferenceById(userDetails.getId())
        );

        if (product.isNutrientUnitLiquid() && product.getDensity() == null) {
            product.setDensity(
                    productDensityService.getDensityByProductName(product.getName())
            );
        }

        return productMapper.toResponse(
                productRepository.save(
                        productValidationService.validate(product)
                )
        );
    }

    public ProductResponse updateProduct(Long id, ProductPatchRequest request) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        productMapper.patch(product, request);

        return productMapper.toResponse(
                productRepository.save(
                        productValidationService.validate(product)
                )
        );
    }

    public ProductResponse getProductById(Long id) {
        return productMapper.toResponse(
                productRepository.findById(id).orElseThrow(
                        () -> new RuntimeException("Product with id " + id + " not found")
                )
        );
    }

   public List<ProductResponse> getProducts(String query, Boolean hasRecipe, Boolean isGlobal) {
        return productMapper.toResponseList(
                productRepository.findProductsByCriteria(
                        query,
                        hasRecipe,
                        isGlobal
                )
        );
   }

    public @Nullable List<ProductResponse> getUserProducts(Long id) {
        return productMapper.toResponseList(
                productRepository.findProductsByOwnerId(id)
        );
    }
}
