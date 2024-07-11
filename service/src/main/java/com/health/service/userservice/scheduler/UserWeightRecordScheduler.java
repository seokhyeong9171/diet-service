package com.health.service.userservice.scheduler;

import com.health.domain.entity.UserEntity;
import com.health.domain.repository.ExerciseRecordRepository;
import com.health.domain.repository.UserRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserWeightRecordScheduler {

  private final UserRepository userRepository;
  private final ExerciseRecordRepository exerciseRecordRepository;


  // 매일 자정에 모든 유저의 exercise duration 정보 업데이트
  @Scheduled(cron = "0 0 0 * * ?")
  public void updateExerciseDuration() {
    List<UserEntity> allUserList = userRepository.findAll();

    allUserList.forEach(user -> {

      // 전날 exercise 데이터 없으면 duration 초기화
      if (!exerciseRecordRepository.existsByUserAndExerciseRecDt(user, LocalDate.now().minusDays(1))) {
        user.resetExerciseDuration();
      }

    });

  }

}
