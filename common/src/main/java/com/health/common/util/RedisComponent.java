package com.health.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisComponent {

  private final RedisTemplate<String, Object> redisTemplate;
  private final ObjectMapper objectMapper;

  public <T> T getData(String key, Class<T> clazz) {

    Object json = redisTemplate.opsForValue().get(key);

    if (json == null) {
      return null;

    } else {

      try {
        return objectMapper.readValue((String) json, clazz);

      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }
    }

  }

  public <T> T setData(String key, T value) {
    ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
    try {
      opsForValue.set(key, objectMapper.writeValueAsString(value));
      return value;
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

}
