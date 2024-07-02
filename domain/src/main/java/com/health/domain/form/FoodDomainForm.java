package com.health.domain.form;

import com.health.domain.type.ConsumeAmount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodDomainForm {

  private String foodCode;

  private ConsumeAmount consumeAmount;

}
