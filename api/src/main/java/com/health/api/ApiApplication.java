package com.health.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@EnableJpaRepositories("com.health")
@EntityScan("com.health")
@SpringBootApplication(scanBasePackages = "com.health")
@EnableScheduling
public class ApiApplication {

  public static void main(String[] args) {

    SpringApplication.run(ApiApplication.class, args);

  }
}
