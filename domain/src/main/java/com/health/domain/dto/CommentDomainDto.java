package com.health.domain.dto;

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
public class CommentDomainDto {

    private Long id;

    private String content;

    private Long postId;

    private String createdUserAuthId;

    private LocalDateTime createdDt;
    private LocalDateTime updateDt;

    private Boolean isDeleted;

    private Long parentId;

    private List<CommentDomainDto> childCommentList;

    public static CommentDomainDto fromEntity(CommentEntity comment) {
        return CommentDomainDto.builder()
            .id(comment.getId())
            .content(comment.getContent())
            .createdUserAuthId(comment.getCreatedUser().getAuthId())
            .createdDt(comment.getCreatedDt())
            .updateDt(comment.getUpdatedDt())
            .isDeleted(comment.getIsDeleted())
            .parentId(comment.getParent().getId())
            .childCommentList(
                comment.getChildCommentList().stream()
                    .map(CommentDomainDto::fromEntity)
                    .toList()
            )
            .build();
    }
}
