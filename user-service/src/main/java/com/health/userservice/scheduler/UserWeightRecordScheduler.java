package com.health.userservice.scheduler;

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

      if (!exerciseRecordRepository.existsByUserAndExerciseRecDt(user, LocalDate.now())) {
        user.resetExerciseDuration();
      }

    });

  }

}
