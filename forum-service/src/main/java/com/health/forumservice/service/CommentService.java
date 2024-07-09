package com.health.forumservice.service;

import com.health.domain.dto.CommentDomainDto;
import com.health.domain.form.CommentDomainForm;
import java.util.List;

public interface CommentService {

  List<CommentDomainDto> getCommentList(Long postId);

  CommentDomainDto createComment(String authId, Long postId, CommentDomainForm domainForm);

  CommentDomainDto createChildComment
      (String authId, Long postId, Long parentCommentId, CommentDomainForm domainForm);

  CommentDomainDto updateComment
      (String authId, Long postId, Long commentId, CommentDomainForm domainForm);

  Long deleteComment(String authId, Long postId, Long commentId);

  CommentDomainDto updateChildComment(
      String authId, Long postId,
      Long parentCommentId, Long childCommentId, CommentDomainForm domainForm
  );

  Long deleteChildComment(String authId, Long postId, Long parentCommentId, Long childCommentId);
}
