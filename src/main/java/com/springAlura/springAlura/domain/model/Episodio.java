package com.springAlura.springAlura.domain.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@NoArgsConstructor
@Table(name = "episodios")
public class Episodio {

	public Episodio(Long id, String titulo, Integer temporada, LocalDate released, Integer numeroEpisodio,
			String avaliacao, Serie serie) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.dataLancamento = released;
		this.numeroEpisodio = numeroEpisodio;
		this.avaliacao = avaliacao;
		this.serie = serie;
		this.temporada = temporada;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	@JsonAlias("Title")
	private String titulo;

	@JsonAlias("Season")
	private Integer temporada;

	@JsonAlias("Released")
	private LocalDate dataLancamento;

	@JsonAlias("Episode")
	private Integer numeroEpisodio;

	@JsonAlias("ImdbRating")
	private String avaliacao;

	@ManyToOne()
	@JoinColumn(name = "serie_id")
	@ToString.Exclude
	private Serie serie;

}
