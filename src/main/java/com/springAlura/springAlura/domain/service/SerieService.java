package com.springAlura.springAlura.domain.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springAlura.springAlura.api.dto.request.SerieRequestDto;
import com.springAlura.springAlura.api.dto.response.SerieResponseDto;
import com.springAlura.springAlura.domain.exception.SerieNaoEncontradaException;
import com.springAlura.springAlura.domain.model.Serie;
import com.springAlura.springAlura.domain.repositories.SerieRepository;

import jakarta.transaction.Transactional;

@Service
public class SerieService {

	@Transactional
	public Serie buscaOuFalha(Long sereId) {
		return repository.findById(sereId).orElseThrow(() -> new SerieNaoEncontradaException(sereId));
	}

	@Autowired
	SerieRepository repository;

	@Transactional
	public Serie salvar(Serie serie) {
		return repository.save(serie);
	}

	@Transactional
	public void deletar(Long serieId) {
		Serie serie = repository.findById(serieId).orElseThrow(() -> new SerieNaoEncontradaException(serieId));

		repository.deleteById(serie.getId());
	}

	@Transactional
	public Serie atualizar(Long serieId, Serie serie) {
		Serie serieAtual = buscaOuFalha(serieId);

		serieAtual.setAtores(serie.getAtores());
		serieAtual.setSinopse(serie.getSinopse());
		serieAtual.setTitulo(serie.getTitulo());
		serieAtual.setAvaliacao(serie.getAvaliacao());
		serieAtual.setTotalTemporada(serie.getTotalTemporada());
		serieAtual.setPoster(serie.getPoster());

		serieAtual = salvar(serieAtual);
		return serieAtual;
	}

	@Transactional
	public SerieResponseDto toDto(Serie serie) {
		SerieResponseDto dto = new SerieResponseDto();
		// forma manual
//		SerieResponseDto dto = new SerieResponseDto(serieAtual.getId(), serieAtual.getTitle(),
//				serieAtual.getTotalSeasons(), serieAtual.getImdbRating(), serieAtual.getActors(),
//				serieAtual.getPoster(), serieAtual.getPlot());

		BeanUtils.copyProperties(serie, dto, "series");

		return dto;
	}

	@Transactional
	public Serie toDomain(SerieRequestDto SerieRequestDto) {
		Serie serie = new Serie();
		// forma manual
//		SerieResponseDto dto = new SerieResponseDto(serieAtual.getId(), serieAtual.getTitle(),
//				serieAtual.getTotalSeasons(), serieAtual.getImdbRating(), serieAtual.getActors(),
//				serieAtual.getPoster(), serieAtual.getPlot());

		BeanUtils.copyProperties(SerieRequestDto, serie);

		return serie;
	}

	@Transactional
	public List<SerieResponseDto> toDtoList(List<Serie> series) {
		// forma manual
//		List<SerieResponseDto> listDto = series.stream().map(s -> new SerieResponseDto(s.getId(), s.getTitle(),
//				s.getTotalSeasons(), s.getImdbRating(), s.getActors(), s.getPoster(), s.getPlot())).toList();

		List<SerieResponseDto> listDto = series.stream().map(s -> {
			SerieResponseDto dto = new SerieResponseDto();
			BeanUtils.copyProperties(s, dto, "series");
			return dto;
		}).toList();

		return listDto;
	}

	public List<Serie> listar() {
		return repository.findAllByOrderByIdAsc();
	}

	public List<Serie> listarTop5() {
		return repository.findTop5ByOrderByAvaliacaoDesc();
	}

	public List<Serie> listarLancamentos() {
		return repository.findTop5ByOrderByDataLancamentoDesc();
	}

	public void imprimirListaSerie(List<Serie> series) {
		for (Serie s : series) {
			System.out.println("\n------------------------------------------------");
			System.out.println("🎬 SÉRIE: " + s.getTitulo() + " | ⭐ NOTA: " + s.getAvaliacao());
			System.out.println("👥 ATORES: " + s.getAtores());
			System.out.println("🖼️ POSTER: " + s.getPoster());
			System.out.println("📖 SINOPSE: " + s.getSinopse());
			System.out.println("📖 ID: " + s.getId());
			System.out.println("------------------------------------------------");
		}
	}

	public void imprimirSerie(Serie serie) {
		System.out.println("\n------------------------------------------------");
		System.out.println("🎬 SÉRIE: " + serie.getTitulo() + " | ⭐ NOTA: " + serie.getAvaliacao());
		System.out.println("👥 ATORES: " + serie.getAtores());
		System.out.println("🖼️ POSTER: " + serie.getPoster());
		System.out.println("📖 SINOPSE: " + serie.getSinopse());
		System.out.println("📖 ID: " + serie.getId());
		System.out.println("------------------------------------------------");

	}

}
