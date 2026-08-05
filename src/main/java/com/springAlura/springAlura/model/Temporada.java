package com.springAlura.springAlura.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosTemporada(Integer season, List<DadosEpisodio> episodes) {

	

}
