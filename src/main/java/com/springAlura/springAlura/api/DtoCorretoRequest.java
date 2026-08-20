package com.springAlura.springAlura.api;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.springAlura.springAlura.domain.model.Categoria;

public record DtoCorretoRequest(Long id, String titulo, Integer totalTemporada, Double avaliacao, String atores,
		String poster, String sinopse, Categoria categoria,
		@JsonFormat(pattern = "dd/MM/yyyy") LocalDate dataLancamento, Boolean ativo) {

}
