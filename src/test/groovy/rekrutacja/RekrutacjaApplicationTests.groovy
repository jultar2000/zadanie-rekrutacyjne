package rekrutacja

import com.example.rekrutacja.domain.MovieMapper
import com.example.rekrutacja.domain.MovieRepository
import com.example.rekrutacja.domain.MovieService
import com.example.rekrutacja.domain.client.DataOmdbClient
import com.example.rekrutacja.domain.config.OmdbConfig
import com.example.rekrutacja.domain.dto.MovieDto
import com.example.rekrutacja.domain.entity.Movie
import com.example.rekrutacja.domain.exception.MovieAlreadyInFavouritesException
import com.example.rekrutacja.domain.exception.MovieNotFoundException
import spock.lang.Specification

class RekrutacjaApplicationTests extends Specification {

    MovieRepository movieRepository = Mock()
    DataOmdbClient dataOmdbClient = Mock()
    OmdbConfig omdbConfig = Mock()
    MovieMapper movieMapper = Mock()
    MovieService movieService = new MovieService(movieRepository, dataOmdbClient, omdbConfig, movieMapper)

    def "should return list of favourite movies"() {
        given:
            Movie movie = buildTestMovieObject()
            List<Movie> movies = [movie]
            MovieDto movieDto = buildTestMovieDtoObject()
            movieRepository.findAll(_ as org.springframework.data.jpa.domain.Specification<Movie>) >> movies
            movieMapper.toDto(_ as Movie) >> movieDto
        when:
            List<MovieDto> result = movieService.getFavouriteMovies()
        then:
            result.size() == 1
            result.get(0) == movieDto
    }

    def "getMovie when movie exists should return MovieDto"() {
        given:
            String title = "Test Movie"
            Movie movie = buildTestMovieObject()
            MovieDto movieDto = buildTestMovieDtoObject()
            movieRepository.findByTitle(title) >> Optional.of(movie)
            movieMapper.toDto(_ as Movie) >> movieDto
        when:
            MovieDto result = movieService.getMovie(title)
        then:
            result == movieDto
    }

    def "throw MovieNotFoundException when movie does not exist"() {
        given:
            String title = "Nonexistent Movie"
            movieRepository.findByTitle(title) >> Optional.empty()
            dataOmdbClient.searchMovie(title, omdbConfig.getKey()) >> buildTestMovieDtoObject(title: null)
        when:
            movieService.getMovie(title)
        then:
            thrown(MovieNotFoundException)
    }

    def "throw MovieAlreadyInFavouritesException when movie already exists"() {
        given:
            String title = "Existing Movie"
            movieRepository.existsByTitle(title) >> true
        when:
            movieService.addToFavourites(title)
        then:
            thrown(MovieAlreadyInFavouritesException)
    }

    def "should save new movie"() {
        given:
            String title = "New Movie"
            MovieDto movieDto = buildTestMovieDtoObject()
            movieRepository.existsByTitle(title) >> false
            dataOmdbClient.searchMovie(title, omdbConfig.getKey()) >> movieDto
        when:
            movieService.addToFavourites(title)
        then:
            1 * movieRepository.save(_)
    }

    static MovieDto buildTestMovieDtoObject(Map params = [:]) {
        return new MovieDto(
                params.getOrDefault("title", "title") as String,
                "genre",
                "plot",
                "director",
                "poster"
        )
    }

    static Movie buildTestMovieObject() {
        return new Movie(
                null,
                "title",
                "plot",
                "genre",
                "director",
                "poster",
                false
        )
    }
}


