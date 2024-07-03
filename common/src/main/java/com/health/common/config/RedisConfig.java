package com.health.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
@EnableCaching
@RequiredArgsConstructor
public class RedisConfig {

  private final ObjectMapper objectMapper;
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
  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    objectMapper.registerModule(new JavaTimeModule());

    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(new StringRedisSerializer());
    template.setHashKeySerializer(new StringRedisSerializer());
    template.setHashValueSerializer(new StringRedisSerializer());
    return template;
  }

  @Bean
  public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {

    RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
        .serializeKeysWith(RedisSerializationContext
            .SerializationPair.fromSerializer(new StringRedisSerializer()))
        .serializeValuesWith(RedisSerializationContext
            .SerializationPair.fromSerializer(new StringRedisSerializer()));

    return RedisCacheManager
        .builder(redisConnectionFactory).cacheDefaults(redisCacheConfiguration).build();
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
