package com.springAlura.springAlura.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosEpisodio(String title, @JsonAlias("Released") LocalDate released, Integer episode,
		String imdbRating) {

}
