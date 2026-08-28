package com.springAlura.springAlura.domain.exception;

public class EntidadeNulaNoPayloadException extends RuntimeException {

	public EntidadeNulaNoPayloadException(String msg) {
		super(msg);
	}

	public EntidadeNulaNoPayloadException(Long serieId) {
		this(String.format("No seu recurso %d contém uma entidade nula.. ", serieId));
	}
}
