package com.starbun.petproject1.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonMapperConfig {
  @Bean
  public ObjectMapper getJsonMapper(){
    return new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
  };
}
