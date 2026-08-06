package com.springAlura.springAlura.repositories;

public interface ConverterDados {

	<T> T converterDadosParaObjeto(String json, Class<T> classe);
}
