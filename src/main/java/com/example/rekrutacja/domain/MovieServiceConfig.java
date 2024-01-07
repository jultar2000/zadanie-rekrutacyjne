package com.example.rekrutacja.domain;

import com.example.rekrutacja.domain.client.DataOmdbClient;
import com.example.rekrutacja.domain.config.OmdbConfig;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MovieServiceConfig {

  @Bean
  MovieService movieService(
      MovieRepository movieRepository,
      DataOmdbClient dataOmdbClient,
      OmdbConfig omdbConfig
  ) {
    var mapper = Mappers.getMapper(MovieMapper.class);
    return new MovieService(
        movieRepository,
        dataOmdbClient,
        omdbConfig,
        mapper
    );
  }
}