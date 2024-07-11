package com.health.mealservice.form;

import com.health.domain.type.MealType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MealServiceForm {

  private MealType mealType;

}
