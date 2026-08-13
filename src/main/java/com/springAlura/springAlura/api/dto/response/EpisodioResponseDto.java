package com.springAlura.springAlura.api.dto.response;

import lombok.Data;

@Data
public class EpisodioResponseDto {

	public EpisodioResponseDto(Integer temporada, Integer numeroEpisodio, String titulo) {
		super();
		this.temporada = temporada;
		this.numeroEpisodio = numeroEpisodio;
		this.titulo = titulo;
	}

	Integer temporada;
	Integer numeroEpisodio;
	String titulo;

}
