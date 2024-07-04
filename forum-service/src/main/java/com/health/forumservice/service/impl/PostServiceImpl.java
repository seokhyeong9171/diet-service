package com.health.forumservice.service.impl;

import com.health.domain.dto.PostDomainDto;
import com.health.domain.repository.PostRepository;
import com.health.forumservice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

  private final PostRepository postRepository;

  @Override
  @Transactional(readOnly = true)
  public Page<PostDomainDto> getPostList(Pageable pageable) {
    return postRepository.findAll(pageable).map(post -> PostDomainDto.fromEntity(post, null));
  }
}
