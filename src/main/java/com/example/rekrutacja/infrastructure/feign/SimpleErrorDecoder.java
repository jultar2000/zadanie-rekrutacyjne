package com.example.rekrutacja.infrastructure.feign;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class SimpleErrorDecoder implements ErrorDecoder {

  //TODO this is written unfortunately in wain. It would work if omdb api would return good response codes, but it returns
  // response code 200 only with some message that request failed. (probably bad built api)
  @Override
  public Exception decode(String methodKey, Response response) {
    String responseAsString = getResponseAsString(response);
    String message = String.format(
        "Request: [%s] returned error with status: [%s] and body: [%s]",
        response.request().url(),
        response.status(),
        responseAsString);
    log.error("error response: {}", responseAsString);
    log.error("message: {}", message);

    return new ExternalServiceException(message, response.status(), responseAsString);
  }

  private static String getResponseAsString(Response response) {
    try {
      return new String(response.body().asInputStream().readAllBytes());
    } catch (Exception e) {
      return "Could not extract response body";
    }
  }
}
