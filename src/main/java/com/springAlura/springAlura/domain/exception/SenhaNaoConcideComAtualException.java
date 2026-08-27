package com.springAlura.springAlura.domain.exception;

public class SenhaNaoConcideComAtualException extends RuntimeException {

	public SenhaNaoConcideComAtualException(String msg) {
		super(msg);
	}

	public SenhaNaoConcideComAtualException(Long serieId) {
		this(String.format("A senha atual não coincide com a atual para o id:", serieId));
	}
}
