package com.springAlura.springAlura.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Temporada {
	private Integer season;
	private List<Episodio> episodes;
}
