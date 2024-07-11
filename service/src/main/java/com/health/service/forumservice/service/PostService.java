package com.health.service.forumservice.service;

import com.health.service.forumservice.dto.PostServiceDto;
import com.health.service.forumservice.form.PostServiceForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

  Page<PostServiceDto> getPostList(Pageable pageable);

  PostServiceDto createPost(String authId, PostServiceForm serviceForm);

  PostServiceDto updatePost(String authId, Long postId, PostServiceForm serviceForm);

  Long deletePost(String authId, Long postId);

  int getPostLikeCount(Long postId);

  int getPostViewCount(Long postId);

  PostServiceDto getPostInfo(String authId, Long postId);

  Integer postAddLike(String authId, Long postId);

  Integer postUnLike(String authId, Long postId);
}
