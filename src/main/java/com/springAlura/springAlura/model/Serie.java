package com.springAlura.springAlura.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@JsonPropertyOrder({ "id", "title", "totalSeasons", "imdbRating", "actors", "poster", "plot", "episodios" })
public class Serie {

	@JsonCreator
	public Serie(@JsonProperty("id") Long id, @JsonProperty("Title") String titulo,
			@JsonProperty("totalSeasons") Integer totalTemporadas, @JsonProperty("imdbRating") String avaliacao,
			@JsonProperty("Actors") String atores, @JsonProperty("Poster") String poster,
			@JsonProperty("Plot") String sinopse) {
		super();
		this.id = id;

		if (!avaliacao.contains("N/A")) {
			this.imdbRating = Double.parseDouble(avaliacao);
		} else {
			this.imdbRating = 0.0;
		}

		this.title = titulo;
		this.totalSeasons = totalTemporadas;
		this.actors = atores;
		this.poster = poster;
		this.plot = sinopse;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	private Integer totalSeasons;

	private Double imdbRating;

	private String actors;

	private String poster;

	private String plot;

	@OneToMany(mappedBy = "serie", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Column(nullable = false)
	@JsonIgnore
	private List<Episodio> episodios = new ArrayList<>();

}
