package org.example.foodbudgetbackendspring.product;

import lombok.RequiredArgsConstructor;
import org.example.foodbudgetbackendspring.product.dto.ProductPatchRequest;
import org.example.foodbudgetbackendspring.product.dto.ProductRequest;
import org.example.foodbudgetbackendspring.product.dto.ProductResponse;
import org.example.foodbudgetbackendspring.product.model.Product;
import org.example.foodbudgetbackendspring.user.UserService;
import org.example.foodbudgetbackendspring.user.model.CustomUserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final ProductDensityService productDensityService;
    private final ProductValidationService productValidationService;

    public Product getProductReference(UUID id) {
        return productRepository.getReferenceById(id);
    }

    public ProductResponse mapToResponse(Product product) {
        return productMapper.toResponse(product);
    }

    public ProductResponse addProduct(ProductRequest request, CustomUserDetails userDetails) {
        Product product = productMapper.toEntity(
                request,
                userService.getUserReference(userDetails.getId())
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

    public ProductResponse updateProduct(UUID id, ProductPatchRequest request) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        productMapper.patch(product, request);

        return productMapper.toResponse(
                productRepository.save(
                        productValidationService.validate(product)
                )
        );
    }

    public ProductResponse getProductById(UUID id) {
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

    public List<ProductResponse> getUserProducts(UUID id, boolean hasRecipe) {
        return productMapper.toResponseList(
                hasRecipe?
                        productRepository.findByOwnerIdAndRecipeIsNotNull(id) :
                        productRepository.findByOwnerIdAndRecipeIsNull(id)
        );
    }
}
