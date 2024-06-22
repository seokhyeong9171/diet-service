package com.health.api.form;

import com.health.domain.form.UserWeightDomainForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserWeightForm {

  private Double weight;


  public UserWeightDomainForm toDomainForm() {
    return UserWeightDomainForm.builder().weight(weight).build();
  }
}
