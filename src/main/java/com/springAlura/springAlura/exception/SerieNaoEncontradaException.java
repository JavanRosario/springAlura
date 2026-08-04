package com.springAlura.springAlura.exception;

public class SerieNaoEncontradaException extends RuntimeException {
	String msg;

	public SerieNaoEncontradaException(String m) {
		this.msg = m;
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return this.msg;
	}

}
