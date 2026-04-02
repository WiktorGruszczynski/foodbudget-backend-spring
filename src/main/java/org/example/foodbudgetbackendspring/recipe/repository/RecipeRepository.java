package org.example.foodbudgetbackendspring.recipe.repository;

import org.example.foodbudgetbackendspring.recipe.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("SELECT r FROM Recipe r " +
            "LEFT JOIN FETCH r.ingredients i " +
            "LEFT JOIN FETCH i.product " + // Pobiera produkty będące składnikami
            "LEFT JOIN FETCH r.product " +     // KLUCZOWE: Pobiera produkt wynikowy przepisu
            "WHERE r.id = :id")
    Optional<Recipe> findByIdWithIngredientsAndProducts(@Param("id") Long id);
}
