package com.health.domain.repository;

import com.health.domain.entity.MeetingParticipantEntity;
import com.health.domain.entity.UserEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingParticipantRepository extends JpaRepository<MeetingParticipantEntity, Long> {

  @EntityGraph(attributePaths = {"meeting", "participant"})
  Optional<MeetingParticipantEntity> findById(Long id);

  @EntityGraph(attributePaths = {"meeting", "participant"})
  List<MeetingParticipantEntity> findByParticipant(UserEntity participant);

}
