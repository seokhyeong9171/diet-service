package com.health.common.redis;

import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class RedisKeyComponent {

  // 유저의 권장 칼로리, 해당 일 섭취 칼로리 조회
  public static String intakeKey(String authId, LocalDate date) {
    return "intake:" + date + ":" + authId;
  }

  // 게시글 좋아요 수
  public static String postLikeCountKey() {
    return "post:like";
  }


  // 게시글 별 좋아요 누른 유저
  public static String userPostLikeKey(String authId) {
    return "user:" + authId + ":like_post";
  }

  // 게시글 조회 수
  public static String postViewCountKey() {
    return "post:view";
  }

  // 유저 별 조회한 게시글
  public static String userPostViewKey(String authId) {
    return "user:" + authId + ":view_post";
  }

}
