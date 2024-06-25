package com.health.domain.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity(name = "food")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class FoodEntity extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private Long id;

    @Column(name = "code")
    private String foodCode;

    @Column(name = "name")
    private String foodName;

    @Column(name = "amount")
    private Integer foodAmount;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "kCal", column = @Column(name = "kcal")),
            @AttributeOverride(name = "carbohydrate", column = @Column(name = "carbohydrate")),
            @AttributeOverride(name = "protein", column = @Column(name = "protein")),
            @AttributeOverride(name = "fat", column = @Column(name = "fat"))
    })
    private Nutrient nutrient;

}
