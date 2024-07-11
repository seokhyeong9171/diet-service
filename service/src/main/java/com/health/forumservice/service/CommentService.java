package com.health.forumservice.service;

import com.health.domain.dto.CommentDomainDto;
import com.health.forumservice.form.CommentServiceForm;
import java.util.List;

public interface CommentService {

  List<CommentDomainDto> getCommentList(Long postId);

  CommentDomainDto createComment(String authId, Long postId, CommentServiceForm domainForm);

  CommentDomainDto createChildComment
      (String authId, Long postId, Long parentCommentId, CommentServiceForm domainForm);

  CommentDomainDto updateComment
      (String authId, Long postId, Long commentId, CommentServiceForm domainForm);

  Long deleteComment(String authId, Long postId, Long commentId);

  CommentDomainDto updateChildComment(
      String authId, Long postId,
      Long parentCommentId, Long childCommentId, CommentServiceForm domainForm
  );

  Long deleteChildComment(String authId, Long postId, Long parentCommentId, Long childCommentId);
}
