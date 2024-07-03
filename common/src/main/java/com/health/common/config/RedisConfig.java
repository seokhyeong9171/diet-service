package com.health.common.config;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;


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

}
