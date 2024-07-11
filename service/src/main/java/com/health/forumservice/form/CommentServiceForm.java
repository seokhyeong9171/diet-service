package com.health.forumservice.form;

import com.health.domain.form.CommentDomainForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentServiceForm {

    private String content;

    public CommentDomainForm toDomainForm() {
        return CommentDomainForm.builder().content(content).build();
    }

}
