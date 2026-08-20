package com.springAlura.springAlura.domain.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Serie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String titulo;

	private Integer totalTemporada;

	private Double avaliacao;

	private String atores;

	private String poster;

	@Column(columnDefinition = "TEXT")
	private String sinopse;

	@JsonFormat(pattern = "yyyy-mm-dd")
	private LocalDate dataLancamento;

	@Column(columnDefinition = "boolean default true")
	private Boolean ativo = true;

	@ManyToOne()
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;

	@ManyToMany
	@JoinTable(name = "series_streamings", joinColumns = @JoinColumn(name = "serie_id"), inverseJoinColumns = @JoinColumn(name = "streaming_id"))
	private List<Streaming> streaming;

}
