package com.springAlura.springAlura.api.dto.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SerieResponseDto {

	@NotNull
	private Long id;

	@Schema(description = "Título oficial da série", example = "Breaking Bad", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotBlank
	@Size(max = 100)
	private String titulo;

	@Schema(description = "Quantidade total de temporadas lançadas", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotNull
	@PositiveOrZero
	private Integer totalTemporada;

	@Schema(description = "Nota de avaliação da série", example = "9.5", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotNull
	@PositiveOrZero
	private Double avaliacao;

	@Schema(description = "Nomes dos principais atores do elenco", example = "Bryan Cranston, Aaron Paul", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotBlank
	private String atores;

	@Schema(description = "URL do poster ou imagem de capa da série", example = "https://link-da-imagem.com", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotBlank
	private String poster;

	@Schema(description = "Sinopse ou resumo do enredo", example = "Um professor de química se volta para o crime...", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotBlank
	private String sinopse;

	@Schema(description = "Data do lançamento original no formato DD/MM/AAAA", example = "20/01/2008", type = "string", requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonFormat(pattern = "dd/MM/yyyy")
	@NotNull
	private LocalDate dataLancamento;

}
