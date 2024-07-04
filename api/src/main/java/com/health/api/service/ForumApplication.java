package com.health.api.service;

import com.health.api.form.PostForm;
import com.health.domain.dto.PostDomainDto;
import com.health.forumservice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ForumApplication {

  private final PostService postService;

  public Page<PostDomainDto> getPostList(Pageable pageable) {
    return postService.getPostList(pageable);
  }

  public PostDomainDto createPost(String authId, PostForm postForm) {
    return postService.createPost(authId, postForm.toDomainForm());
  }
}
