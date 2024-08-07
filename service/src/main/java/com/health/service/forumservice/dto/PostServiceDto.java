package com.health.service.forumservice.dto;

import com.health.domain.entity.PostEntity;
import com.health.domain.entity.UserEntity;
import com.health.domain.type.PostCategory;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostServiceDto {

    private Long id;

    private PostCategory postCategory;

    private String title;

    private String content;

    private Integer like;
    private Integer view;

    private LocalDateTime postCreateDt;
    private LocalDateTime postUpdateDt;

    private CreatedUser createdUser;

    public static PostServiceDto fromEntity(PostEntity post) {
        return PostServiceDto.builder()
            .id(post.getId())
            .postCategory(post.getPostCategory())
            .title(post.getTitle())
            .content(post.getContent())
            .postCreateDt(post.getPostCreateDt())
            .postUpdateDt(post.getPostUpdateDt())
            .createdUser(CreatedUser.fromUserEntity(post.getCreateUser()))
            .build();
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CreatedUser {
        private String authId;
        private String nickname;

        public static CreatedUser fromUserEntity(UserEntity user) {
            return CreatedUser.builder()
                .authId(user.getAuthId())
                .nickname(user.getNickname())
                .build();
        }
    }

}
