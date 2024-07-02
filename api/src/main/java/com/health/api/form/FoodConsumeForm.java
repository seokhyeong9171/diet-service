package com.health.api.form;

import com.health.domain.form.FoodDomainForm;
import com.health.domain.type.ConsumeAmount;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodConsumeForm {

  @NotNull
  private String foodCode;

  @NotNull
  private ConsumeAmount consumeAmount;


  public FoodDomainForm toDomainForm() {
    return FoodDomainForm.builder().foodCode(foodCode).consumeAmount(consumeAmount).build();
  }
}
