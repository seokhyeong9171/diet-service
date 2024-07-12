package com.health.redisservice.aspect;

import static com.health.domain.exception.ErrorCode.*;
import static com.health.redisservice.component.RedisKeyComponent.*;

import com.health.domain.exception.CustomException;
import com.health.redisservice.annotation.KeyType;
import com.health.redisservice.annotation.RedissonLock;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor
public class RedissonLockAspect {

  private final RedissonClient redissonClient;

  @Around("@annotation(redissonLock)")
  public Object handleRedissonLockAnnotation(
      ProceedingJoinPoint joinPoint, RedissonLock redissonLock
  ) throws Throwable {

    String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
    Object[] args = joinPoint.getArgs();

    // value로 넘어온 이름의 파라미터 찾음
    String parameterValue = null;
    for (int i = 0; i < parameterNames.length; i++) {
      if (parameterNames[i].equals(redissonLock.value())) {
        parameterValue = args[i].toString();
        break;
      }
    }
    validateParameterValue(parameterValue);

    RLock lock = redissonClient.getLock(getRedissonKey(redissonLock.key(), parameterValue));

    try {
      boolean isLocked = lock.tryLock(10, 10, TimeUnit.SECONDS);

      validateLockTimeout(isLocked);

      return joinPoint.proceed();

    } catch (RuntimeException | InterruptedException e) {
      throw new RuntimeException(e);

    } finally {
      lock.unlock();
    }
  }

  // 파라미터로 넘어온 값이 올바른지 확인
  private void validateParameterValue(String parameterValue) {
    if (parameterValue == null) {
      throw new CustomException(REDIS_LOCK_KEY_INVALID);
    }
  }

  // lock timeout 확인
  private void validateLockTimeout(boolean isLocked) {
    if (!isLocked) {
      throw new CustomException(REDIS_LOCK_TIMEOUT);
    }
  }

  private String getRedissonKey(KeyType keyType, String parameterValue) {

    if (Objects.requireNonNull(keyType) == KeyType.MEETING_ENROLL) {
      return meetingEnrollRock(parameterValue);
    }

    throw new CustomException(REDIS_LOCK_KEY_INVALID);
  }


}
