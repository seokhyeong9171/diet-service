package com.health.domain.entity;

import com.health.domain.dto.FoodDomainDto;
import com.health.domain.dto.NutrientDomainDto;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "foods")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder @Getter
public class FoodElasticEntity {

//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "food_id")
//    private Long code;

    @Id
//    @Field(type = FieldType.Text)
    private String id;

    @Field(type = FieldType.Text, analyzer = "nori")
    private String foodName;

    @Field(type = FieldType.Integer)
    private Integer foodAmount;

    private Nutrient nutrient;

    public static FoodElasticEntity fromEntity(FoodEntity foodEntity) {
        return FoodElasticEntity.builder()
            .id(foodEntity.getFoodCode())
            .foodName(foodEntity.getFoodName())
            .foodAmount(foodEntity.getFoodAmount())
            .nutrient(foodEntity.getNutrient())
            .build();
    }

    public FoodDomainDto toDomainDto() {
        return FoodDomainDto.builder()
            .foodCode(id)
            .foodName(foodName)
            .foodAmount(foodAmount)
            .nutrientDomainDto(NutrientDomainDto.fromEntity(nutrient))
            .build();
    }

}
