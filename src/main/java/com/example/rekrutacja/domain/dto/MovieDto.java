package com.example.rekrutacja.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
public record MovieDto(@JsonProperty("Title") String title,
                       @JsonProperty("Genre") String genre,
                       @JsonProperty("Plot") String plot,
                       @JsonProperty("Director") String director,
                       @JsonProperty("Poster") String poster) {

}
