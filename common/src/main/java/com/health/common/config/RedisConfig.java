package com.health.common.config;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
@EnableCaching
@RequiredArgsConstructor
public class RedisConfig {

  private final Environment env;

  @Bean
  public RedisConnectionFactory connectionFactory() {
    RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
    redisConfig.setHostName(getRedisHost());
    redisConfig.setPort(getRedisPort());
    redisConfig.setUsername(getRedisUsername());
    redisConfig.setPassword(getRedisPassword());
    return new LettuceConnectionFactory(redisConfig);
  }

  @Bean
  public RedisTemplate<String, Integer> hashRedisTemplate() {
    RedisTemplate<String, Integer> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(connectionFactory());
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new StringRedisSerializer());
    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    redisTemplate.setHashValueSerializer(new StringRedisSerializer());
    return redisTemplate;
  }

  @Bean
  public HashOperations<String, String, Integer> hashOperations(RedisTemplate<String, Integer> redisTemplate) {
    return redisTemplate.opsForHash();
  }

  @Bean
  public RedissonClient redissonClient() {
    Config config = new Config();
    config.useSingleServer()
        .setAddress(getRedissonAddress())
        .setUsername(getRedisUsername()).setPassword(getRedisPassword());

    return Redisson.create(config);
  }

  private String getRedisHost() {
    return env.getProperty("spring.data.redis.host");
  }

  private int getRedisPort() {
    return Integer.parseInt(Objects.requireNonNull(env.getProperty("spring.data.redis.port")));
  }

  private String getRedisUsername() {
    return env.getProperty("spring.data.redis.username");
  }

  private String getRedisPassword() {
    return env.getProperty("spring.data.redis.password");
  }

  private String getRedissonAddress() {
    return "redis://" + getRedisHost() + ":" + getRedisPort();
  }

}
