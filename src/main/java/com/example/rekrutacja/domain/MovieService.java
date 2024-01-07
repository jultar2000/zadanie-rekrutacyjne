package com.example.rekrutacja.domain;

import com.example.rekrutacja.domain.client.DataOmdbClient;
import com.example.rekrutacja.domain.config.OmdbConfig;
import com.example.rekrutacja.domain.dto.MovieDto;
import com.example.rekrutacja.domain.entity.Movie;
import com.example.rekrutacja.domain.exception.MovieAlreadyInFavouritesException;
import com.example.rekrutacja.domain.exception.MovieNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public class MovieService {

  private final MovieRepository movieRepository;
  private final DataOmdbClient dataOmdbClient;
  private final OmdbConfig omdbConfig;
  private final MovieMapper movieMapper;

  public List<MovieDto> getFavouriteMovies() {
    return movieRepository
        .findAll(buildFavouriteMovieSpecification())
        .stream()
        .map(movieMapper::toDto)
        .collect(Collectors.toList());
  }

  public MovieDto getMovie(String title) {
    Optional<Movie> movie = movieRepository.findByTitle(title);
    if (movie.isPresent()) {
      return movieMapper.toDto(movie.get());
    }
    MovieDto movieDto = dataOmdbClient.searchMovie(title, omdbConfig.getKey());
    //TODO: The reason for this approach is described in the ExternalServiceException class.
    // It utilizes the fact that if an OMDB API request doesn't return a title (a parameter we MUST search with),
    // then everything else is nonsensical, indicating that some problem must have occurred.
    if (Objects.isNull(movieDto.title())) {
      throw new MovieNotFoundException(title);
    }
    return movieDto;
  }

  @Transactional
  public void addToFavourites(String title) {
    if (movieRepository.existsByTitle(title)) {
      throw new MovieAlreadyInFavouritesException(title);
    }
    MovieDto movieDto = dataOmdbClient.searchMovie(title, omdbConfig.getKey());
    if (Objects.isNull(movieDto.title())) {
      throw new MovieNotFoundException(title);
    }
    Movie movie = Movie.builder()
        .title(movieDto.title())
        .director(movieDto.director())
        .genre(movieDto.genre())
        .plot(movieDto.plot())
        .poster(movieDto.poster())
        .favourite(true)
        .build();
    movieRepository.save(movie);
  }

  private Specification<Movie> buildFavouriteMovieSpecification() {

    return (root, query, cb) -> cb.isTrue(root.get("favourite"));
  }
}
