package com.springAlura.springAlura.domain.repositories;

public interface ConverterDados {

	<T> T converterDadosParaObjeto(String json, Class<T> classe);
}
