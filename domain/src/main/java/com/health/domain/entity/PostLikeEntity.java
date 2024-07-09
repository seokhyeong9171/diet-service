package com.health.domain.entity;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "post_like")
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Getter @Builder
public class PostLikeEntity {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "post_like_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private UserEntity likedUser;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id")
  private PostEntity post;

  public static PostLikeEntity createNew(UserEntity likedUser, PostEntity post) {
    return PostLikeEntity.builder()
        .likedUser(likedUser).post(post)
        .build();
  }

}
