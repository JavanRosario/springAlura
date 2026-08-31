package com.springAlura.springAlura.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record SerieFiltroRequestDto(@JsonProperty(required = false) @NotEmpty String titulo,
		@JsonProperty(required = false) @PositiveOrZero Double notaMax) {
}
