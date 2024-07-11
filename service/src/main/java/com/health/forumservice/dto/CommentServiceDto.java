package com.health.forumservice.dto;

import com.health.domain.entity.CommentEntity;
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
public class CommentServiceDto {

    private Long id;

    private String content;

    private Long postId;

    private String createdUserAuthId;

    private LocalDateTime createdDt;
    private LocalDateTime updateDt;

    private boolean isDeleted;

    private Long parentId;

    private List<CommentServiceDto> childCommentList;

    public static CommentServiceDto fromEntity(CommentEntity comment) {

        // 삭제된 comment의 경우 comment 내용 가져오지 않음
        if (comment.isDeleted()) {
            return CommentServiceDto.builder()
                .id(comment.getId())
                .isDeleted(comment.isDeleted())
                .postId(comment.getPost().getId())
                .content("삭제된 댓글입니다.")
                .build();

        } else {
            return CommentServiceDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdUserAuthId(comment.getCreatedUser().getAuthId())
                .createdDt(comment.getCreatedDt())
                .updateDt(comment.getUpdatedDt())
                .isDeleted(comment.isDeleted())
                .parentId(comment.getParent().getId())
                .childCommentList(
                    comment.getChildCommentList().stream()
                        .map(CommentServiceDto::fromEntity)
                        .toList()
                )
                .build();
        }
    }
}
