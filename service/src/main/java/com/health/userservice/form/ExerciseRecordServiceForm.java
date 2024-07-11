package com.health.userservice.form;

import com.health.domain.form.ExerciseRecordDomainForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExerciseRecordServiceForm {

  private String description;

  public ExerciseRecordDomainForm toDomainForm() {
    return ExerciseRecordDomainForm.builder().description(description).build();
  }

}
