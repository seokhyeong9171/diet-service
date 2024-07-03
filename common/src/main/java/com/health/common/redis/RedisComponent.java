package com.health.common.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisComponent {

//  private final RedisTemplate<String, Object> redisTemplate;
//  private final ObjectMapper objectMapper;
//
//  public <T> T getData(String key, Class<T> clazz) {
//
//    Object json = redisTemplate.opsForValue().get(key);
//
//    if (json == null) {
//      return null;
//
//    } else {
//
//      try {
//        return objectMapper.readValue((String) json, clazz);
//
//      } catch (JsonProcessingException e) {
//        throw new RuntimeException(e);
//      }
//    }
//
//  }
//
//  public <T> T setData(String key, T value) {
//    ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
//    try {
//      opsForValue.set(key, objectMapper.writeValueAsString(value));
//      return value;
//    } catch (JsonProcessingException e) {
//      throw new RuntimeException(e);
//    }
//  }
//
//  public Boolean deleteData(String key) {
//    return redisTemplate.delete(key);
//  }

}
