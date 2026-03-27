package org.example.foodbudgetbackendspring.product.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private Float quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MeasurementUnit quantityUnit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MeasurementUnit nutrientUnit;

    private Float density;

    // Nutrients
    @Column(nullable = false)
    private Float energyKcal;
    private Float fat;
    private Float saturatedFat;
    private Float carbohydrates;
    private Float sugars;
    private Float fiber;
    private Float protein;
    private Float salt;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
