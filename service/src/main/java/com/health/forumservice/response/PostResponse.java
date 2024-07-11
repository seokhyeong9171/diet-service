package com.health.forumservice.response;

import com.health.domain.type.PostCategory;
import com.health.forumservice.dto.PostServiceDto;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class PostResponse {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PostListResponse {
        private Long id;
        private PostCategory postCategory;
        private String title;
        private Integer like;

        private LocalDateTime postCreateDt;
        private LocalDateTime postUpdateDt;

        private String createdUserNickname;

        public static PostResponse.PostListResponse fromDomainDto(PostServiceDto postServiceDto) {
            return PostListResponse.builder()
                .id(postServiceDto.getId())
                .postCategory(postServiceDto.getPostCategory())
                .title(postServiceDto.getTitle())
                .like(postServiceDto.getLike())
                .postCreateDt(postServiceDto.getPostCreateDt())
                .postUpdateDt(postServiceDto.getPostUpdateDt())
                .createdUserNickname(postServiceDto.getCreatedUser().getNickname())
                .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PostContentResponse {

        private Long id;

        private PostCategory postCategory;

        private String title;

        private String content;

        private LocalDateTime postCreateDt;
        private LocalDateTime postUpdateDt;

        private String createdUserNickname;

        public static PostResponse.PostContentResponse fromDomainDto(PostServiceDto postServiceDto) {
            return PostContentResponse.builder()
                .id(postServiceDto.getId())
                .postCategory(postServiceDto.getPostCategory())
                .title(postServiceDto.getTitle())
                .content(postServiceDto.getContent())
                .postCreateDt(postServiceDto.getPostCreateDt())
                .postUpdateDt(postServiceDto.getPostUpdateDt())
                .createdUserNickname(postServiceDto.getCreatedUser().getNickname())
                .build();
        }
    }

}
