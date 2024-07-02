package com.health.domain.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "daily_meal")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class DailyMealEntity extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "daily_meal_id")
    private Long id;

    @Column(name = "consume_dt")
    private LocalDate dailyMealDt;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "kCal", column = @Column(name = "total_kcal")),
            @AttributeOverride(name = "carbohydrate", column = @Column(name = "total_carbohydrate")),
            @AttributeOverride(name = "protein", column = @Column(name = "total_protein")),
            @AttributeOverride(name = "fat", column = @Column(name = "total_fat"))
    })
    private Nutrient nutrient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "dailyMeal")
    private List<MealEntity> meals = new ArrayList<>();

    public static DailyMealEntity createNew(UserEntity userEntity, LocalDate date) {
        return DailyMealEntity.builder()
            .dailyMealDt(date)
            .nutrient(Nutrient.createNew())
            .user(userEntity)
            .build();
    }

    public void addNutrient(ConsumeFoodEntity consumeFood) {
        this.nutrient.addNutrient(consumeFood.getNutrient());
    }

    public void minusNutrient(ConsumeFoodEntity consumeFood) {
        this.nutrient.minusNutrient(consumeFood.getNutrient());
    }
}
