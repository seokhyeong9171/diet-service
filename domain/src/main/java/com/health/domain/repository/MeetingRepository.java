package com.health.domain.repository;

import com.health.domain.entity.MeetingEntity;
import com.health.domain.type.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRepository extends JpaRepository<MeetingEntity, Long> {

  @Query(
      value = "select m from meeting m where m.meetingArea.region = :region",
      countQuery = "select count(*) from meeting m where m.meetingArea.region = :region"
  )
  Page<MeetingEntity> findAllByRegion(@Param("region") Region region, Pageable pageable);

}
