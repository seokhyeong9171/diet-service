package com.health.domain.response;

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

  private Long id;

  private String content;

  private String createdUserAuthId;

  private LocalDateTime createdDt;
  private LocalDateTime updateDt;

  private List<CommentResponse> childCommentList;


  public static CommentListResponse fromDomainDto(CommentDomainDto commentDomainDto) {
    return CommentListResponse.builder()
        .id(commentDomainDto.getId())
        .content(commentDomainDto.getContent())
        .createdUserAuthId(commentDomainDto.getCreatedUserAuthId())
        .createdDt(commentDomainDto.getCreatedDt())
        .updateDt(commentDomainDto.getUpdateDt())
        .childCommentList(
            commentDomainDto.getChildCommentList().stream()
                .map(CommentResponse::fromDomainDto).toList()
        )
        .build();
  }

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class CommentResponse {

    private Long id;

    private String content;

    private String createdUserAuthId;
    private LocalDateTime createdDt;
    private LocalDateTime updateDt;

    public static CommentResponse fromDomainDto(CommentDomainDto commentDomainDto) {
      return CommentResponse.builder()
          .id(commentDomainDto.getId())
          .content(commentDomainDto.getContent())
          .createdUserAuthId(commentDomainDto.getCreatedUserAuthId())
          .createdDt(commentDomainDto.getCreatedDt())
          .updateDt(commentDomainDto.getUpdateDt())
          .build();
    }

  }


}
