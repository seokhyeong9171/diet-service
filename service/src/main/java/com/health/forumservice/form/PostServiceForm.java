package com.health.forumservice.form;

import com.health.domain.type.PostCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostServiceForm {

    private PostCategory postCategory;

    private String title;

    private String content;

}
