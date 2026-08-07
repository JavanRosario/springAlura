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

	@Transactional
	public void deletar(Long serieId) {
		repository.deleteById(serieId);
	}

	@Transactional
	public Serie atualizar(Long serieId, Serie serie) {
		Serie serieAtual = buscaOuFalha(serieId);

		serieAtual.setActors(serie.getActors());
		serieAtual.setPlot(serie.getPlot());
		serieAtual.setTitle(serie.getTitle());
		serieAtual.setImdbRating(serie.getImdbRating());
		serieAtual.setTotalSeasons(serie.getTotalSeasons());
		serieAtual.setPoster(serie.getPoster());

		serieAtual = salvar(serieAtual);
		return serieAtual;
	}

	public List<Serie> listar() {
		return repository.findAll();
	}

	public void imprimirListaSerie(List<Serie> series) {
		for (Serie s : series) {
			System.out.println("\n------------------------------------------------");
			System.out.println("🎬 SÉRIE: " + s.getTitle() + " | ⭐ NOTA: " + s.getImdbRating());
			System.out.println("👥 ATORES: " + s.getActors());
			System.out.println("🖼️ POSTER: " + s.getPoster());
			System.out.println("📖 SINOPSE: " + s.getPlot());
			System.out.println("📖 ID: " + s.getId());
			System.out.println("------------------------------------------------");
		}
	}

	public void imprimirSerie(Serie serie) {
		System.out.println("\n------------------------------------------------");
		System.out.println("🎬 SÉRIE: " + serie.getTitle() + " | ⭐ NOTA: " + serie.getImdbRating());
		System.out.println("👥 ATORES: " + serie.getActors());
		System.out.println("🖼️ POSTER: " + serie.getPoster());
		System.out.println("📖 SINOPSE: " + serie.getPlot());
		System.out.println("📖 ID: " + serie.getId());
		System.out.println("------------------------------------------------");

	}

}
