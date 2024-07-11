package com.health.elasticservice.dto;

import com.health.domain.entity.FoodEntity;
import com.health.domain.entity.Nutrient;
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
public class FoodElasticDto {

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

    public static FoodElasticDto fromEntity(FoodEntity foodEntity) {
        return FoodElasticDto.builder()
            .id(foodEntity.getFoodCode())
            .foodName(foodEntity.getFoodName())
            .foodAmount(foodEntity.getFoodAmount())
            .nutrient(foodEntity.getNutrient())
            .build();
    }

//    public FoodDomainDto toDomainDto() {
//        return FoodDomainDto.builder()
//            .foodCode(id)
//            .foodName(foodName)
//            .foodAmount(foodAmount)
//            .nutrientDomainDto(NutrientDomainDto.fromEntity(nutrient))
//            .build();
//    }

}
