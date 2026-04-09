package org.example.foodbudgetbackendspring.recipe;

import org.example.foodbudgetbackendspring.recipe.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
interface RecipeRepository extends JpaRepository<Recipe, UUID> {

    @Query("SELECT r FROM Recipe r " +
            "LEFT JOIN FETCH r.ingredients i " +
            "LEFT JOIN FETCH i.product " + // Pobiera produkty będące składnikami
            "LEFT JOIN FETCH r.product " +     // KLUCZOWE: Pobiera produkt wynikowy przepisu
            "WHERE r.id = :id")
    Optional<Recipe> findByIdWithIngredientsAndProducts(@Param("id") UUID id);
}
