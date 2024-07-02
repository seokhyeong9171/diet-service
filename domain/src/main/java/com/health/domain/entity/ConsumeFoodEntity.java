package com.health.domain.entity;

import com.health.domain.type.ConsumeAmount;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "consume_food")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ConsumeFoodEntity extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "consume_food_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meal_id")
    private MealEntity meal;

    @Column(name = "amount")
    @Enumerated(value = EnumType.STRING)
    private ConsumeAmount consumeAmount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_code")
    private FoodEntity food;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "kCal", column = @Column(name = "kcal")),
        @AttributeOverride(name = "carbohydrate", column = @Column(name = "carbohydrate")),
        @AttributeOverride(name = "protein", column = @Column(name = "protein")),
        @AttributeOverride(name = "fat", column = @Column(name = "fat"))
    })
    private Nutrient nutrient;

    public static ConsumeFoodEntity
    create(MealEntity meal, FoodEntity food, ConsumeAmount consumeAmount) {
        return ConsumeFoodEntity.builder()
            .meal(meal).food(food).consumeAmount(consumeAmount)
            .nutrient(calculateNutrient(food, consumeAmount))
            .build();
    }

    private static Nutrient calculateNutrient(FoodEntity food, ConsumeAmount consumeAmount) {
        Nutrient foodNutrient = food.getNutrient();
        Double amountValue = consumeAmount.getValue();

        return Nutrient.builder()
            .kCal((int) (foodNutrient.getKCal() * amountValue))
            .carbohydrate(foodNutrient.getCarbohydrate() * amountValue)
            .protein(foodNutrient.getProtein() * amountValue)
            .fat(foodNutrient.getFat() * amountValue)
            .build();
    }
}
