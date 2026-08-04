package com.springAlura.springAlura.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosTemporada(Integer season, List<DadosEpisodio> episodes) {

	public static void imprimirSeason2(List<DadosTemporada> dadosTemporada) {

		for (int i = 0; i < dadosTemporada.size(); i++) {
			System.out.printf("Temporada: %d===========================\n".formatted(dadosTemporada.get(i).season));
			for (int j = 0; j < dadosTemporada.get(i).episodes.size(); j++) {
				System.out.print(
						"Episodios= %s========================\n".formatted(dadosTemporada.get(i).episodes.toString()));
			}
		}

	}

	public void imprimirTitulosSeason() {
		System.out.println("Episodios:");

		episodes.forEach(s -> System.out.println(s.title()));
	}

	public void imprimirTemporadas() {
		for (int i = 0; i < episodes.size(); i++) {
			System.out.println("Season: %d Episodes: %s".formatted(this.season, episodes.toString()));
		}
	}

}
