package com.springAlura.springAlura.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.springAlura.springAlura.api.dto2.CategoriaResponseDto;
import com.springAlura.springAlura.api.dto2.SerieRequestDto;
import com.springAlura.springAlura.api.dto2.SerieResponseDto;
import com.springAlura.springAlura.api.especification.SerieEspecification;
import com.springAlura.springAlura.domain.exception.EntidadeNulaNoPayloadException;
import com.springAlura.springAlura.domain.exception.SerieNaoEncontradaException;
import com.springAlura.springAlura.domain.model.Categoria;
import com.springAlura.springAlura.domain.model.Serie;
import com.springAlura.springAlura.domain.model.Streaming;
import com.springAlura.springAlura.domain.repositories.SerieRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SerieService {

	@Autowired
	CategoriaService categoriaService;

	@Autowired
	StreamingService streamingService;

	public void associarStreaming(Long serieId, Long streamingId) {
		Serie serie = buscaOuFalha(serieId);

		Streaming streaming = streamingService.buscaOuFalha(streamingId);
		serie.getStreaming().add(streaming);
		salvar(serie);
	}

	public List<Serie> buscaComFiltros(String nome, Double notaMax, Integer limite) {

		Specification<Serie> filtros = Specification.where(SerieEspecification.porNome(nome))
				.and(SerieEspecification.porNota(notaMax));

		int limitePadrao = (limite != null) ? limite : 10;

		Pageable limiteRegistros = PageRequest.of(0, limitePadrao);

		return repository.findAll(filtros, limiteRegistros).getContent();

	}

	@Transactional
	public void ativarSerie(Long serieId) {
		log.debug("Iniciando o processo de ativação da Série de ID: {}", serieId);
		Serie serie = buscaOuFalha(serieId);
		serie.setAtivo(true);
		salvar(serie);
	}

	@Transactional
	public void desativarSerie(Long serieId) {
		log.debug("Iniciando o processo de desativação da Série de ID: {}", serieId);
		Serie serie = buscaOuFalha(serieId);
		serie.setAtivo(false);
		salvar(serie);
	}

	@Transactional
	public Serie buscaOuFalha(Long serieId) {
		log.debug("Iniciando a busca para o ID:{}", serieId);

		return repository.findById(serieId).orElseThrow(() -> {
			log.warn("Falha na consulta: Série com ID {} não existe no seu sistema", serieId);
			return new SerieNaoEncontradaException(serieId);
		});
	}

	@Autowired
	SerieRepository repository;

	@Transactional
	public Serie salvar(Serie serie) {
		log.debug("Iniciando processo para salvar a serie: '{}'", serie.getTitulo());
		Long categoriaId = Optional.ofNullable(serie).map(Serie::getCategoria).map(Categoria::getId)
				.orElseThrow(() -> new EntidadeNulaNoPayloadException(serie.getId()));

		log.debug("Validando a existência da categoria com ID: {}", categoriaId);
		Categoria categoria = categoriaService.buscaOuFalha(categoriaId);

		serie.setCategoria(categoria);

		Serie serieSalva = repository.save(serie);
		log.info("Série '{}' salva com sucesso! ID gerado no banco: {}", serieSalva.getTitulo(), serieSalva.getId());
		return serieSalva;
	}

	@Transactional
	public void deletar(Long serieId) {
		Serie serie = repository.findById(serieId).orElseThrow(() -> new SerieNaoEncontradaException(serieId));

		repository.deleteById(serie.getId());
	}

	@Transactional
	public Serie atualizar(Long serieId, Serie serie) {
		log.debug("Iniciando processo para atualização do recurso da Série '{}' de ID: {}", serie.getTitulo(), serieId);
		Serie serieAtual = buscaOuFalha(serieId);

		BeanUtils.copyProperties(serie, serieAtual, "id");

		log.info("Série '{}' atualizada com sucesso!", serieAtual.getTitulo());

		serieAtual = salvar(serieAtual);
		return serieAtual;
	}

	public SerieResponseDto toDto(Serie serie) {
		SerieResponseDto dto = new SerieResponseDto();

		Categoria categoria = serie.getCategoria();
		CategoriaResponseDto categoriaResponseDto = categoriaService.toDto(categoria);

		// forma manual
//		SerieResponseDto dto = new SerieResponseDto(serieAtual.getId(), serieAtual.getTitle(),
//				serieAtual.getTotalSeasons(), serieAtual.getImdbRating(), serieAtual.getActors(),
//				serieAtual.getPoster(), serieAtual.getPlot());

		BeanUtils.copyProperties(serie, dto, "series");
		dto.setCategoriaId(categoriaResponseDto);
		return dto;
	}

	public Serie toDomain(SerieRequestDto serieRequestDto) {
		Serie serie = new Serie();
		Categoria categoria = categoriaService.buscaOuFalha(serieRequestDto.getCategoriaId().getId());

		// forma manual
//		SerieResponseDto dto = new SerieResponseDto(serieAtual.getId(), serieAtual.getTitle(),
//				serieAtual.getTotalSeasons(), serieAtual.getImdbRating(), serieAtual.getActors(),
//				serieAtual.getPoster(), serieAtual.getPlot());
		serie.setCategoria(categoria);
		BeanUtils.copyProperties(serieRequestDto, serie);

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

			if (s.getCategoria() != null) {
				CategoriaResponseDto categoriaDto = categoriaService.toDto(categoria);
				dto.setCategoriaId(categoriaDto);
			}

			BeanUtils.copyProperties(s, dto);
			return dto;

		}).toList();

		return listDto;
	}

	public List<Serie> listar() {
		log.debug("Acessando o repositório para buscar as séries ordenadas por ID.");
		List<Serie> series = repository.findAllByOrderByIdAsc();
		log.info("Busca de séries finalizada. Total de registros encontrados: {}", series.size());
		return series;
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
