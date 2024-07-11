package com.health.userservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UserRedisTemplateConfig {

//  @Bean
//  public RedisTemplate<String, IntakeRedisDto>
//  IntakeDomainDtoRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
//    RedisTemplate<String, IntakeRedisDto> template = new RedisTemplate<>();
//    template.setConnectionFactory(redisConnectionFactory);
//    template.setKeySerializer(new StringRedisSerializer());
//    template.setValueSerializer(new Jackson2JsonRedisSerializer<>(IntakeRedisDto.class));
//    template.setHashKeySerializer(new StringRedisSerializer());
//    template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(IntakeRedisDto.class));
//    return template;
//  }
//
//  @Bean
//  public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
//
//    RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
//        .serializeKeysWith(RedisSerializationContext
//            .SerializationPair.fromSerializer(new StringRedisSerializer()))
//        .serializeValuesWith(RedisSerializationContext
//            .SerializationPair.fromSerializer(
//                new Jackson2JsonRedisSerializer<>(IntakeRedisDto.class))
//        );
//
//    return RedisCacheManager
//        .builder(redisConnectionFactory).cacheDefaults(redisCacheConfiguration).build();
//  }

}
