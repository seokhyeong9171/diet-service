package com.health.api.form;

import com.health.domain.form.PostDomainForm;
import com.health.domain.type.PostCategory;
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
public class PostForm {

    @NotNull
    private PostCategory postCategory;

    @NotNull
    @Size(min = 1, max = 20)
    private String title;

    @NotNull
    @Size(min = 1, max = 200)
    private String content;

    public PostDomainForm toDomainForm() {
        return PostDomainForm.builder()
            .postCategory(postCategory).title(title).content(content)
            .build();
    }

}
