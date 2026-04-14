package org.example.foodbudgetbackendspring.meal;

import org.example.foodbudgetbackendspring.meal.model.MealItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MealItemRepository extends JpaRepository<MealItem, Long> {

    @Query("SELECT mi FROM MealItem mi " +
            "JOIN mi.meal m " +
            "WHERE mi.id = :mealItemId AND m.user.id = :userId")
    Optional<MealItem> findByIdAndUserId(
            @Param("mealItemId") UUID id,
            @Param("userId") UUID userId
    );
}
