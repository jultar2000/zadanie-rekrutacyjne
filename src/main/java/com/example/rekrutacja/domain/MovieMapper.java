package com.example.rekrutacja.domain;

import com.example.rekrutacja.domain.dto.MovieDto;
import com.example.rekrutacja.domain.entity.Movie;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface MovieMapper {

  MovieDto toDto(Movie movie);

}
