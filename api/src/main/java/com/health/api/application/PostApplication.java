package com.health.api.application;

import com.health.api.form.PostForm;
import com.health.service.forumservice.dto.PostServiceDto;
import com.health.service.forumservice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostApplication {

  private final PostService postService;

  public Page<PostServiceDto> getPostList(Pageable pageable) {
    return postService.getPostList(pageable);
  }

  public PostServiceDto createPost(String authId, PostForm postForm) {
    return postService.createPost(authId, postForm.toDomainForm());
  }

  public PostServiceDto updatePost(String authId, Long postId, PostForm postForm) {
    return postService.updatePost(authId, postId, postForm.toDomainForm());
  }

  public Long deletePost(String authId, Long postId) {
    return postService.deletePost(authId, postId);
  }

  public Integer getPostLikeCount(Long postId) {
    return postService.getPostLikeCount(postId);
  }

  public Integer getPostViewCount(Long postId) {
    return postService.getPostViewCount(postId);
  }

  public PostServiceDto getPostInfo(String authId, Long postId) {
    return postService.getPostInfo(authId, postId);
  }

  public Integer postAddLike(String authId, Long postId) {
    return postService.postAddLike(authId, postId);
  }

  public Integer postUnLike(String authId, Long postId) {
    return postService.postUnLike(authId, postId);
  }
}
