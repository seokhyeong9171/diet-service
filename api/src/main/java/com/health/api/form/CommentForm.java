package com.health.api.form;

import com.health.service.forumservice.form.CommentServiceForm;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentForm {

    @NotNull
    @Size(min = 1, max = 200)
    private String content;

    public CommentServiceForm toDomainForm() {
        return CommentServiceForm.builder().content(content).build();
    }
}
