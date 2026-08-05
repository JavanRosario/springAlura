package com.springAlura.springAlura.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springAlura.springAlura.model.Serie;
import com.springAlura.springAlura.repositories.SerieRepository;

import jakarta.transaction.Transactional;

@Service
public class SerieService {

	@Transactional
	public Serie buscaOuFalha(Long sereId) {
		return repository.findById(sereId).orElseThrow(() -> new IllegalArgumentException("Não achei o id"));
	}

	@Autowired
	SerieRepository repository;

	@Transactional
	public Serie salvar(Serie serie) {
		return repository.save(serie);
	}

	public List<Serie> listar() {
		return repository.findAll();
	}

}
