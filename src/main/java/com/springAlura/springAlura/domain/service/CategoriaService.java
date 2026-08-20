package com.springAlura.springAlura.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springAlura.springAlura.api.dto2.CategoriaResponseDto;
import com.springAlura.springAlura.domain.exception.SerieNaoEncontradaException;
import com.springAlura.springAlura.domain.model.Categoria;
import com.springAlura.springAlura.domain.repositories.CategoriaRepository;

import jakarta.transaction.Transactional;

@Service
public class CategoriaService {

	@Autowired
	CategoriaRepository categoriaRepository;

	@Transactional
	public Categoria buscaOuFalha(Long categoriaId) {
		return categoriaRepository.findById(categoriaId)
				.orElseThrow(() -> new SerieNaoEncontradaException(categoriaId));
	}

	@Transactional
	public CategoriaResponseDto toDto(Categoria categoria) {
		CategoriaResponseDto categoriaResponseDto = new CategoriaResponseDto();

		BeanUtils.copyProperties(categoria, categoriaResponseDto);

		return categoriaResponseDto;
	}

}
