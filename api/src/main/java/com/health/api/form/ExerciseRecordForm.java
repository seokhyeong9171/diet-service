package com.health.api.form;

import com.health.domain.form.ExerciseRecordDomainForm;
import jakarta.validation.constraints.NotBlank;
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


  public ExerciseRecordDomainForm toDomainForm() {
    return ExerciseRecordDomainForm.builder().description(description).build();
  }
}
