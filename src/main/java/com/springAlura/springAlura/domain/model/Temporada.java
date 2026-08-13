package com.springAlura.springAlura.domain.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Temporada {

	@JsonAlias("Season")
	private Integer temporada;

	@JsonAlias("Episodes")
	private List<Episodio> episodios;
}
