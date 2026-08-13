package com.springAlura.springAlura.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springAlura.springAlura.api.dto.response.EpisodioResponseDto;
import com.springAlura.springAlura.domain.model.Episodio;
import com.springAlura.springAlura.domain.repositories.EpisodioRepositoryy;

import jakarta.transaction.Transactional;

@Service
public class EpisodioService {

	@Autowired
	EpisodioRepositoryy episodioRepositoryy;

	@Transactional
	public Episodio salvar(Episodio episodio) {
		return episodioRepositoryy.save(episodio);
	}

	@Transactional
	public EpisodioResponseDto toDto(Episodio episodio) {
		EpisodioResponseDto dto = new EpisodioResponseDto(episodio.getTemporada(), episodio.getNumeroEpisodio(),
				episodio.getTitulo());
		return dto;
	}

	@Transactional
	public List<EpisodioResponseDto> toDtoList(List<Episodio> episodios) {
		// forma manual
		List<EpisodioResponseDto> listDto = episodios.stream()
				.map(s -> new EpisodioResponseDto(s.getTemporada(), s.getNumeroEpisodio(), s.getTitulo())).toList();

		List<Integer> episodiosNovos = episodios.stream().filter(s -> s.getTemporada() > 2).map(Episodio::getTemporada)
				.toList();

		return listDto;
	}

	public void imprimirEpisodio(List<Episodio> list) {

		for (Episodio episodio : list) {
			System.out.println("\n------------------------------------------------");
			System.out.println("🎬 EPISÓDIO: " + episodio.getTitulo() + " | ⭐ NOTA: " + episodio.getAvaliacao());
			System.out.println("📖 ID: " + episodio.getId() + " | 📅 LANÇAMENTO: " + episodio.getDataLancamento());
			System.out.println("🔢 NÚMERO DO EP: " + episodio.getNumeroEpisodio());
			if (episodio.getTitulo() != null) {
				System.out.println("📺 SÉRIE PERTENCENTE: " + episodio.getSerie().getTitulo());
			} else {
				System.out.println("📺 SÉRIE PERTENCENTE: Não informada");
			}
			System.out.println("------------------------------------------------");
		}
	}

}
