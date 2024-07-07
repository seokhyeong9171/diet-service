package com.health.domain.repository;

import com.health.domain.entity.CommentEntity;
import com.health.domain.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

  @Modifying
  @Query("delete from comment c where c.post = :post")
  void deleteByPost(@Param("post") PostEntity post);

  @Modifying
  @Query("delete from comment cc where cc.parent = :parentComment")
  void deleteByParentComment(@Param("parentComment")CommentEntity parentComment);

}
