package com.health.domain.form;

import com.health.domain.type.PostCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDomainForm {

    private PostCategory postCategory;

    private String title;

    private String content;

}
