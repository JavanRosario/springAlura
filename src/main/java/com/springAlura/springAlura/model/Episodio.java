package com.springAlura.springAlura.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "episodios")
public class Episodio {

	public Episodio(Long id, String title, LocalDate released, Integer episode, String imdbRating, Serie serie) {
		super();
		this.id = id;
		this.title = title;
		this.released = released;
		this.episode = episode;
		this.imdbRating = imdbRating;
		this.serie = serie;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	private String title;

	private LocalDate released;

	private Integer episode;

	private String imdbRating;

	@ManyToOne()
	@JoinColumn(name = "serie_id")
	private Serie serie;

}
