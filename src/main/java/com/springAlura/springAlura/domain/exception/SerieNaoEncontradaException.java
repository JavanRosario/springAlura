package com.springAlura.springAlura.domain.exception;

public class SerieNaoEncontradaException extends RuntimeException {

	public SerieNaoEncontradaException(String msg) {
		super(msg);
	}

	public SerieNaoEncontradaException(Long serieId) {
		this(String.format("Não existe um cadastro de Serie com código: %d", serieId));
	}
}
