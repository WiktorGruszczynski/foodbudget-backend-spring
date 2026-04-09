package org.example.foodbudgetbackendspring.product;

import org.example.foodbudgetbackendspring.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
interface ProductRepository extends JpaRepository<Product, UUID> {
    @Query("SELECT p FROM Product p WHERE " +
            "(:query IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%'))) AND " +
            "(:hasRecipe IS NULL OR (:hasRecipe = true AND p.recipe IS NOT NULL) OR (:hasRecipe = false AND p.recipe IS NULL)) AND " +
            "(:isGlobal IS NULL OR p.isGlobal = :isGlobal)")
    List<Product> findProductsByCriteria(
            @Param("query") String query,
            @Param("hasRecipe") Boolean hasRecipe,
            @Param("isGlobal") Boolean isGlobal
    );

    List<Product> findByOwnerId(UUID id);
    List<Product> findByOwnerIdAndRecipeIsNull(UUID ownerId);
    List<Product> findByOwnerIdAndRecipeIsNotNull(UUID ownerId);
}
