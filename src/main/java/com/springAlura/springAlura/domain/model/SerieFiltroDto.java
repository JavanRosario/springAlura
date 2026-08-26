package com.springAlura.springAlura.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SerieFiltroDto(@JsonProperty(required = false) String titulo,
		@JsonProperty(required = false) Double notaMax, Integer limite) {
	
}
