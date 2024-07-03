package com.health.api.form;

import com.health.domain.form.MealDomainForm;
import com.health.domain.type.MealType;
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

  public MealDomainForm toDomainForm() {
    return MealDomainForm.builder().mealType(mealType).build();
  }

}
