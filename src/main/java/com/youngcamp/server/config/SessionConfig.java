package com.youngcamp.server.config;

import java.util.concurrent.ConcurrentHashMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;

@Configuration
public class SessionConfig {

  @Bean
  public SessionRepository<?> sessionRepository() {
    return new MapSessionRepository(new ConcurrentHashMap<>());
  }
}
