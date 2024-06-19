package com.health.repository;

import com.health.entity.CommentEntity;
import com.health.entity.MeetingParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingParticipantRepository extends JpaRepository<MeetingParticipantEntity, Long> {

}
