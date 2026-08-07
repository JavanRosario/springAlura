package com.springAlura.springAlura.service;

import java.util.List;

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

	public void imprimirEpisodio(List<Episodio> list) {

		for (Episodio episodio : list) {
			System.out.println("\n------------------------------------------------");
			System.out.println("🎬 EPISÓDIO: " + episodio.getTitle() + " | ⭐ NOTA: " + episodio.getImdbRating());
			System.out.println("📖 ID: " + episodio.getId() + " | 📅 LANÇAMENTO: " + episodio.getReleased());
			System.out.println("🔢 NÚMERO DO EP: " + episodio.getEpisode());
			if (episodio.getTitle() != null) {
				System.out.println("📺 SÉRIE PERTENCENTE: " + episodio.getSerie().getTitle());
			} else {
				System.out.println("📺 SÉRIE PERTENCENTE: Não informada");
			}
			System.out.println("------------------------------------------------");
		}
	}

}
