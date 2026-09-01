package com.springAlura.springAlura.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.PositiveOrZero;

public record SerieFiltroRequestDto(@JsonProperty(required = false) String titulo,
		@JsonProperty(required = false) @PositiveOrZero Double notaMax, String atores) {
}
