package com.springAlura.springAlura.domain.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Serie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@NotNull
	private String titulo;

	@NotNull
	@PositiveOrZero
	private Integer totalTemporada;

	@NotNull
	@PositiveOrZero
	private Double avaliacao;

	private String atores;

	private String poster;

	private String sinopse;

	@Column(nullable = false)
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataLancamento;

	private Boolean ativo;

	@OneToMany(mappedBy = "serie", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Column(nullable = false)
	private List<Episodio> episodios = new ArrayList<>();

}
