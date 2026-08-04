package com.springAlura.springAlura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(String title, Integer totalSeasons, Double imdbRating, String genre, String director,
		String actors, String poster, String plot) {

}
