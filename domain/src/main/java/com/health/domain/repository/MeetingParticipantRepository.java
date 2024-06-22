package com.health.domain.repository;

import com.health.domain.entity.MeetingParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingParticipantRepository extends JpaRepository<MeetingParticipantEntity, Long> {

}
