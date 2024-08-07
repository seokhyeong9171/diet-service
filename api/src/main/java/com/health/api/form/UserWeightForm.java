package com.health.api.form;

import com.health.service.userservice.form.UserWeightServiceForm;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserWeightForm {

  @NotNull
  private Double weight;

  public UserWeightServiceForm toDomainForm() {
    return UserWeightServiceForm.builder().weight(weight).build();
  }
}
