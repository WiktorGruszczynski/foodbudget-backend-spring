package org.example.foodbudgetbackendspring.product.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.example.foodbudgetbackendspring.product.dto.ProductPatchRequest;
import org.example.foodbudgetbackendspring.product.dto.ProductRequest;
import org.example.foodbudgetbackendspring.product.dto.ProductResponse;
import org.example.foodbudgetbackendspring.product.service.ProductService;
import org.example.foodbudgetbackendspring.user.model.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> addProduct(
            @Valid @RequestBody ProductRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        return ResponseEntity
                .status(201)
                .body(
                        productService.addProduct(request, userDetails)
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(
            @PathVariable("id") UUID id
    ){
        return ResponseEntity.ok(
                productService.getProductById(id)
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable("id") UUID id,
            @RequestBody ProductPatchRequest request
    ){
        return ResponseEntity.ok(
                productService.updateProduct(id, request)
        );
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts(
            @RequestParam(value = "query") @Size(min=3, message="Query is too short") String query,
            @RequestParam(value = "hasRecipe", required = false) Boolean hasRecipe,
            @RequestParam(value = "isGlobal", required = false) Boolean isGlobal
    ){
        return ResponseEntity.ok(
                productService.getProducts(query, hasRecipe, isGlobal)
        );
    }

    @GetMapping("/me")
    public ResponseEntity<List<ProductResponse>> getUserProducts(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        return ResponseEntity.ok(
                productService.getUserProducts(userDetails.getId())
        );
    }
}
