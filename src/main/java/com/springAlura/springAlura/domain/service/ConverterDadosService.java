package com.springAlura.springAlura.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springAlura.springAlura.domain.repositories.ConverterDados;

@Service
public class ConverterDadosService implements ConverterDados {

	@Autowired
	ObjectMapper mapper;

	@Override
	public <T> T converterDadosParaObjeto(String json, Class<T> classe) {

		try {
			return mapper.readValue(json, classe);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}

	}

}
