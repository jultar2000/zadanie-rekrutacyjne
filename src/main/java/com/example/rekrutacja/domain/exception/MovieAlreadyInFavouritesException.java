package com.example.rekrutacja.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
@Getter
public class MovieAlreadyInFavouritesException extends RuntimeException {

  public MovieAlreadyInFavouritesException(String title) {
    super(String.format("Movie with title: %s is already in favourites.", title));
  }
}