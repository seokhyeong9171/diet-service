package com.health.api.form;

import com.health.userservice.form.ExerciseRecordServiceForm;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExerciseRecordForm {

  @Size(min = 1, max = 100)
  @NotEmpty
  private String description;


  public ExerciseRecordServiceForm toDomainForm() {
    return ExerciseRecordServiceForm.builder().description(description).build();
  }
}
