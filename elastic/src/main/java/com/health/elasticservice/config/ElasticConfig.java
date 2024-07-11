package com.health.elasticservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@RequiredArgsConstructor
@EnableElasticsearchRepositories(basePackages = "com.health.elasticservice")
public class ElasticConfig extends ElasticsearchConfiguration {
//
  private final Environment env;
//
////  @Override
////  public ClientConfiguration clientConfiguration() {
////    return ClientConfiguration.builder().connectedTo("localhost:9200").build();
////  }
//
//  @Value("${spring.elasticsearch.username}")
//  private String username;
//
//  @Value("${spring.elasticsearch.password}")
//  private String password;
//
//  @Value("${spring.elasticsearch.uris[0]}")
//  private String hostList;
//
  @Override
  public ClientConfiguration clientConfiguration() {

    String uri = env.getProperty("spring.elasticsearch.uris[0]");
    String username = env.getProperty("spring.elasticsearch.username");
    String password = env.getProperty("spring.elasticsearch.password");
    String ssl = env.getProperty("spring.elasticsearch.ssl");

    return ClientConfiguration.builder()
        .connectedTo(uri)
        .usingSsl(ssl)
        .withBasicAuth(username, password)
        .build();
  }
}
