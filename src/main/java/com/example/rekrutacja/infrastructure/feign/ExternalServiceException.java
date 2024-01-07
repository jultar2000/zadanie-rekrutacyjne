package com.example.rekrutacja.infrastructure.feign;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class ExternalServiceException extends RuntimeException {

  private final String code = "external-service-error";
  protected int status;
  protected String responseBody;

  public ExternalServiceException(String message, int statusCode, String responseBody) {
    super(message);
    this.status = statusCode;
    this.responseBody = responseBody;
  }
}
