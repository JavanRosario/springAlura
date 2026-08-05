package com.springAlura.springAlura.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springAlura.springAlura.model.Episodio;
import com.springAlura.springAlura.repositories.EpisodioRepository;

import jakarta.transaction.Transactional;

@Service
public class EpisodioService {

	@Autowired
	EpisodioRepository episodioRepository;

	@Transactional
	public Episodio salvar(Episodio episodio) {
		return episodioRepository.save(episodio);
	}

}
