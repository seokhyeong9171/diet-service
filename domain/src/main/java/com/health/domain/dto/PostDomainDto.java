package com.health.domain.dto;

import com.health.domain.type.PostCategory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDomainDto {

    private Long id;

    private PostCategory postCategory;

    private String title;

    private String content;

    private Integer like;

    private LocalDateTime postCreateDt;
    private LocalDateTime postUpdateDt;

    private UserDomainDto createUser;

    private List<CommentDomainDto> commentList;
}
