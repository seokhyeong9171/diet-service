package com.health.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.health.domain.dto.CommentDomainDto;
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
public class CommentListResponse {

  private CommentInfo parentComment;

  @JsonInclude(Include.NON_EMPTY)
  private List<CommentInfo> childCommentList;


  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class CommentInfo {

    private Long id;

    private String content;

    @JsonInclude(Include.NON_NULL)
    private String createdUserAuthId;

    @JsonInclude(Include.NON_NULL)
    private LocalDateTime createdDt;
    @JsonInclude(Include.NON_NULL)
    private LocalDateTime updateDt;

    public static CommentInfo fromDomainDto(CommentDomainDto commentDomainDto) {

        return CommentInfo.builder()
            .id(commentDomainDto.getId())
            .content(commentDomainDto.getContent())
            .createdUserAuthId(commentDomainDto.getCreatedUserAuthId())
            .createdDt(commentDomainDto.getCreatedDt())
            .updateDt(commentDomainDto.getUpdateDt())
            .build();
    }

  }

  public static CommentListResponse fromDomainDto(CommentDomainDto commentDomainDto) {

    return CommentListResponse.builder()
        .parentComment(CommentInfo.fromDomainDto(commentDomainDto))
        .childCommentList(
            commentDomainDto.getChildCommentList().stream()
                .map(CommentInfo::fromDomainDto).toList()
        )
        .build();
  }

}
