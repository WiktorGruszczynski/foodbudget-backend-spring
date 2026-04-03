package org.example.foodbudgetbackendspring.product.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.foodbudgetbackendspring.recipe.model.Ingredient;
import org.example.foodbudgetbackendspring.recipe.model.Recipe;
import org.example.foodbudgetbackendspring.user.model.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    private String ean;
    private String manufacturer;

    // Measurements
    @Column(nullable = false)
    private Float quantity = 0f;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MeasurementUnit quantityUnit = MeasurementUnit.GRAM;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MeasurementUnit nutrientUnit = MeasurementUnit.GRAM;

    private Float density;

    // Nutrients
    @Column(nullable = false)
    private Float energyKcal = 0f;

    @Column(nullable = false)
    private Float fat = 0f;

    @Column(nullable = false)
    private Float saturatedFat = 0f;

    @Column(nullable = false)
    private Float carbohydrates = 0f;

    @Column(nullable = false)
    private Float sugars = 0f;

    @Column(nullable = false)
    private Float fiber = 0f;

    @Column(nullable = false)
    private Float protein = 0f;

    @Column(nullable = false)
    private Float salt = 0f;

    @Column(precision = 10, scale = 2)
    private BigDecimal price = BigDecimal.ZERO;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(nullable = false)
    private Boolean isGlobal = false;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public boolean isNutrientUnitLiquid() {
        return MeasurementUnit.MILLILITER.equals(this.nutrientUnit);
    }

    public boolean isSolid(){
        return this.nutrientUnit.equals(MeasurementUnit.GRAM) && this.quantityUnit.equals(MeasurementUnit.GRAM);
    }

    public void resetQuantity() {
        this.quantity = 0f;
    }

    public void resetNutrients() {
        this.energyKcal = 0f;
        this.fat = 0f;
        this.saturatedFat = 0f;
        this.carbohydrates = 0f;
        this.sugars = 0f;
        this.fiber = 0f;
        this.protein = 0f;
        this.salt = 0f;
        this.price = new BigDecimal(0);
    }

    /**
     * Adds raw nutrient values based on the ingredient's quantity.
     * WARNING: This method leaves the product in an intermediate state (total sum).
     * Always call {@link #applyScaling(float)} after the calculation loop to normalize to 100g.
     */
    public void addNutrientsFrom(Ingredient ingredient){
        Product otherProduct = ingredient.getProduct();
        float massInGrams = ingredient.getQuantity();

        if (ingredient.getUnit().equals(MeasurementUnit.MILLILITER)) {
            if (otherProduct.getDensity() == null) {
                throw new IllegalStateException("NULL density for liquid product");
            }

            massInGrams *= otherProduct.getDensity();
        }

        float ratio = massInGrams / 100f;

        this.quantity += massInGrams;
        this.energyKcal += otherProduct.getEnergyKcal() * ratio;
        this.fat += otherProduct.getFat() * ratio;
        this.saturatedFat += otherProduct.getSaturatedFat() * ratio;
        this.carbohydrates += otherProduct.getCarbohydrates() * ratio;
        this.sugars += otherProduct.getSugars() * ratio;
        this.fiber += otherProduct.getFiber() * ratio;
        this.protein += otherProduct.getProtein() * ratio;
        this.salt += otherProduct.getSalt() * ratio;


        if (otherProduct.getPrice() != null) {
            this.price = this.price.add(
                    ingredient.getPrice()
            );
        }
    }

    public void applyScaling(float factor) {
        this.energyKcal *= factor;
        this.protein *= factor;
        this.fat *= factor;
        this.carbohydrates *= factor;
        this.sugars *= factor;
        this.saturatedFat *= factor;
        this.fiber *= factor;
        this.salt *= factor;
    }
}
