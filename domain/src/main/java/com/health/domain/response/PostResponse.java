package com.health.domain.response;

import com.health.domain.dto.PostDomainDto;
import com.health.domain.type.PostCategory;
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

        public static PostResponse.PostListResponse fromDomainDto(PostDomainDto postDomainDto) {
            return PostListResponse.builder()
                .id(postDomainDto.getId())
                .postCategory(postDomainDto.getPostCategory())
                .title(postDomainDto.getTitle())
                .like(postDomainDto.getLike())
                .postCreateDt(postDomainDto.getPostCreateDt())
                .postUpdateDt(postDomainDto.getPostUpdateDt())
                .createdUserNickname(postDomainDto.getCreatedUser().getNickname())
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

        public static PostResponse.PostContentResponse fromDomainDto(PostDomainDto postDomainDto) {
            return PostContentResponse.builder()
                .id(postDomainDto.getId())
                .postCategory(postDomainDto.getPostCategory())
                .title(postDomainDto.getTitle())
                .content(postDomainDto.getContent())
                .postCreateDt(postDomainDto.getPostCreateDt())
                .postUpdateDt(postDomainDto.getPostUpdateDt())
                .createdUserNickname(postDomainDto.getCreatedUser().getNickname())
                .build();
        }
    }

}
