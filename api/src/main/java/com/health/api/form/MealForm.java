package com.health.api.form;

import com.health.domain.type.MealType;
import com.health.service.mealservice.form.MealServiceForm;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MealForm {

  @NotNull
  private MealType mealType;

  public MealServiceForm toDomainForm() {
    return MealServiceForm.builder().mealType(mealType).build();
  }

}
