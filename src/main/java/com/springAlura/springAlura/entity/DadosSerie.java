package com.springAlura.springAlura.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(String title, Integer totalSeasons, Double imdbRating) {

}
