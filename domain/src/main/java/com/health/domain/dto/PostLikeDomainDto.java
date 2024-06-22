package com.health.domain.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PostLikeDomainDto {

    private Long id;

    private PostDomainDto post;

    private UserDomainDto likedUser;
}
