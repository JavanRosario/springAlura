package com.springAlura.springAlura.domain.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.springAlura.springAlura.api.dto2.CategoriaResponseDto;
import com.springAlura.springAlura.api.dto2.SerieRequestDto;
import com.springAlura.springAlura.api.dto2.SerieResponseDto;
import com.springAlura.springAlura.api.dto2.StreamingResponseDto;
import com.springAlura.springAlura.api.especification.SerieEspecification;
import com.springAlura.springAlura.domain.exception.SerieNaoEncontradaException;
import com.springAlura.springAlura.domain.model.Categoria;
import com.springAlura.springAlura.domain.model.Serie;
import com.springAlura.springAlura.domain.model.Streaming;
import com.springAlura.springAlura.domain.repositories.SerieRepository;

import jakarta.transaction.Transactional;

@Service
public class SerieService {

	@Autowired
	CategoriaService categoriaService;

	@Autowired
	StreamingService streamingService;

	public List<Serie> buscaComFiltros(String nome, Double notaMax, Integer limite) {

		Specification<Serie> filtros = Specification.where(SerieEspecification.porNome(nome))
				.and(SerieEspecification.porNota(notaMax));

		int limitePadrao = (limite != null) ? limite : 10;

		Pageable limiteRegistros = PageRequest.of(0, limitePadrao);

		return repository.findAll(filtros, limiteRegistros).getContent();

	}

	@Transactional
	public void ativarSerie(Long serieId) {
		Serie serie = buscaOuFalha(serieId);
		serie.setAtivo(true);
		salvar(serie);
	}

	@Transactional
	public void desativarSerie(Long serieId) {
		Serie serie = buscaOuFalha(serieId);
		serie.setAtivo(false);
		salvar(serie);
	}

	@Transactional
	public Serie buscaOuFalha(Long sereId) {
		return repository.findById(sereId).orElseThrow(() -> new SerieNaoEncontradaException(sereId));
	}

	@Autowired
	SerieRepository repository;

	@Transactional
	public Serie salvar(Serie serie) {
		Long categoriaId = serie.getCategoria().getId();
		Categoria categoria = categoriaService.buscaOuFalha(categoriaId);
		serie.setCategoria(categoria);
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

	public SerieResponseDto toDto(Serie serie) {
		SerieResponseDto dto = new SerieResponseDto();

		Categoria categoria = serie.getCategoria();
		CategoriaResponseDto categoriaResponseDto = categoriaService.toDto(categoria);

		List<Streaming> streamings = serie.getStreaming();
		List<StreamingResponseDto> streamingDtos = streamingService.toDtoList(streamings);

		// forma manual
//		SerieResponseDto dto = new SerieResponseDto(serieAtual.getId(), serieAtual.getTitle(),
//				serieAtual.getTotalSeasons(), serieAtual.getImdbRating(), serieAtual.getActors(),
//				serieAtual.getPoster(), serieAtual.getPlot());

		BeanUtils.copyProperties(serie, dto, "series");
		dto.setCategoria(categoriaResponseDto);
		dto.setStreaming(streamingDtos);
		return dto;
	}

	public Serie toDomain(SerieRequestDto SerieRequestDto) {
		Serie serie = new Serie();
		Categoria categoria = categoriaService.buscaOuFalha(SerieRequestDto.getCategoriaIdRequest());

		// forma manual
//		SerieResponseDto dto = new SerieResponseDto(serieAtual.getId(), serieAtual.getTitle(),
//				serieAtual.getTotalSeasons(), serieAtual.getImdbRating(), serieAtual.getActors(),
//				serieAtual.getPoster(), serieAtual.getPlot());

		BeanUtils.copyProperties(SerieRequestDto, serie);
		serie.setCategoria(categoria);
		return serie;
	}

	@Transactional
	public List<SerieResponseDto> toDtoList(List<Serie> series) {
		// forma manual
//		List<SerieResponseDto> listDto = series.stream().map(s -> new SerieResponseDto(s.getId(), s.getTitle(),
//				s.getTotalSeasons(), s.getImdbRating(), s.getActors(), s.getPoster(), s.getPlot())).toList();

		List<SerieResponseDto> listDto = series.stream().map(s -> {

			SerieResponseDto dto = new SerieResponseDto();
			Categoria categoria = s.getCategoria();
			List<Streaming> streamings = s.getStreaming();

			if (s.getCategoria() != null) {
				CategoriaResponseDto categoriaDto = categoriaService.toDto(categoria);
				dto.setCategoria(categoriaDto);
			}

			if (s.getStreaming() != null) {

				List<StreamingResponseDto> streamingDtos = streamingService.toDtoList(streamings);
				dto.setStreaming(streamingDtos);
			}

			BeanUtils.copyProperties(s, dto);
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
