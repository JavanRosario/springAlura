package com.springAlura.springAlura.service;

public interface ConverterDados {

	<T> T converterDadosParaObjeto(String json, Class<T> classe);
}
