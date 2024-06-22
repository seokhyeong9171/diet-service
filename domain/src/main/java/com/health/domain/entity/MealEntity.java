package com.health.domain.entity;

import com.health.domain.type.MealType;
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
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity(name = "meal")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class MealEntity extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meal_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "meal_type")
    private MealType mealType;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "kCal", column = @Column(name = "kcal")),
            @AttributeOverride(name = "carbohydrate", column = @Column(name = "carbohydrate")),
            @AttributeOverride(name = "protein", column = @Column(name = "protein")),
            @AttributeOverride(name = "fat", column = @Column(name = "fat"))
    })
    private Nutrient nutrient;

    @Column(name = "date")
    private LocalDate mealDt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "daily_meal_id")
    private DailyMealEntity dailyMeal;

    @OneToMany(mappedBy = "meal")
    private List<ConsumeFoodEntity> consumeFoodList = new ArrayList<>();
}
