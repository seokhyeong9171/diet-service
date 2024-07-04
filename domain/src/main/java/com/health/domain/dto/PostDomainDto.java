package com.health.domain.dto;

import com.health.domain.entity.PostEntity;
import com.health.domain.entity.UserEntity;
import com.health.domain.type.PostCategory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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

    private UserEntity createUser;

    public static PostDomainDto fromEntity(PostEntity post, Integer like) {
        return PostDomainDto.builder()
            .id(post.getId())
            .postCategory(post.getPostCategory())
            .title(post.getTitle())
            .content(post.getContent())
            .like(like)
            .build();
    }

}
