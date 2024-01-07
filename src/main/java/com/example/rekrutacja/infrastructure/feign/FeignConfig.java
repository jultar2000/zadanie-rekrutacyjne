package com.example.rekrutacja.infrastructure.feign;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class FeignConfig {

  @Bean
  public ErrorDecoder errorDecoder() {
    return new SimpleErrorDecoder();
  }
}