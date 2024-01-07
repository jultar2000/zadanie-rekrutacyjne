package com.example.rekrutacja.domain;

import com.example.rekrutacja.domain.dto.MovieDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/movies")
@RequiredArgsConstructor
public class MovieController {

  private final MovieService movieService;

  @GetMapping("/favourites")
  @ResponseStatus(HttpStatus.OK)
  public List<MovieDto> getFavouriteMovies() {
    return movieService.getFavouriteMovies();
  }

  @GetMapping("/{title}")
  @ResponseStatus(HttpStatus.OK)
  public MovieDto getMovie(@PathVariable(name = "title") String title) {
    return movieService.getMovie(title);
  }

  @PostMapping("/{title}")
  @ResponseStatus(HttpStatus.OK)
  public void addToFavourites(@PathVariable(name = "title") String title) {
    movieService.addToFavourites(title);
  }

}
