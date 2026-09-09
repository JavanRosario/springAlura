package com.springAlura.springAlura.domain.service;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.springAlura.springAlura.api.dto.SerieAuditoriaResponseDto;
import com.springAlura.springAlura.api.dto.SerieFiltroRequestDto;
import com.springAlura.springAlura.api.dto2.SerieResponseDto;
import com.springAlura.springAlura.api.especification.SerieEspecification;
import com.springAlura.springAlura.api.mapper.serie.SerieRequestMapper;
import com.springAlura.springAlura.api.mapper.serie.SerieResponseMapper;
import com.springAlura.springAlura.domain.exception.SerieNaoEncontradaException;
import com.springAlura.springAlura.domain.model.AuditRevisionEntity;
import com.springAlura.springAlura.domain.model.Categoria;
import com.springAlura.springAlura.domain.model.Serie;
import com.springAlura.springAlura.domain.model.Streaming;
import com.springAlura.springAlura.domain.repositories.SerieRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SerieService {

	@Autowired
	CategoriaService categoriaService;

	@Autowired
	StreamingService streamingService;

	@Autowired
	SerieRepository repository;

	@Autowired
	AuditReader auditReader;

	@Autowired
	EntityManager entityManager;

	@Autowired
	SerieRequestMapper requestMapper;

	@Autowired
	SerieResponseMapper responseMapper;

	public List<SerieAuditoriaResponseDto> listarHistoricoSerie(Long serieId) {

		AuditReader auditReader = AuditReaderFactory.get(entityManager);

		@SuppressWarnings("unchecked")
		List<Object[]> rawResults = auditReader.createQuery().forRevisionsOfEntityWithChanges(Serie.class, true)
				.add(AuditEntity.id().eq(serieId)).addOrder(AuditEntity.revisionNumber().asc()).getResultList();

		OffsetDateTime dataCriacaoOriginal = rawResults.stream().filter(resultado -> resultado[2] == RevisionType.ADD)
				.map(resultado -> (AuditRevisionEntity) resultado[1]).map(this::converterDataRevisao).findFirst()
				.orElseGet(() -> rawResults.isEmpty() ? null
						: converterDataRevisao((AuditRevisionEntity) rawResults.get(0)[1]));

		return rawResults.stream().map(resultado -> {
			Serie serie = (Serie) resultado[0];
			AuditRevisionEntity revision = (AuditRevisionEntity) resultado[1];
			RevisionType tipoRevisao = (RevisionType) resultado[2];

			@SuppressWarnings("unchecked")
			Set<String> propriedadesAlteradas = (Set<String>) resultado[3];

			OffsetDateTime dataRevisao = converterDataRevisao(revision);

			String usuarioAuditado = revision.getUser() != null ? revision.getUser() : "Sistema_Sem_Login";

			return new SerieAuditoriaResponseDto(serie.getId(), serie.getTitulo(), revision.getId(), usuarioAuditado,
					tipoRevisao.name(), dataRevisao, dataCriacaoOriginal, propriedadesAlteradas.stream().toList());
		}).toList();
	}

	private OffsetDateTime converterDataRevisao(AuditRevisionEntity revision) {
		return revision.getRevisionDate().toInstant().atZone(ZoneId.systemDefault()).toOffsetDateTime();
	}

	public void associarStreaming(Long serieId, Long streamingId) {
		Serie serie = buscaOuFalha(serieId);

		Streaming streaming = streamingService.buscaOuFalha(streamingId);
		serie.getStreaming().add(streaming);
		salvar(serie);
	}

	public Page<SerieResponseDto> buscaComFiltros(SerieFiltroRequestDto dto, Pageable pageable) {
		log.debug("Iniciando o processo de busca das Séries com filtros");

		Specification<Serie> filtros = Specification.where(SerieEspecification.porNome(dto.titulo()))
				.and(SerieEspecification.porNota(dto.notaMax()).and(SerieEspecification.porAtores(dto.atores())));

		Page<Serie> paginas = repository.findAll(filtros, pageable);
		log.info("Retornando {} valores da busca", paginas.getContent().size());
		return paginas.map(s -> responseMapper.toDto(s));
	}

	@Transactional
	public void ativarSerie(Long serieId) {
		log.debug("Iniciando o processo de ativação da Série de ID: {}", serieId);
		Serie serie = buscaOuFalha(serieId);
		serie.setAtivo(true);
		atualizar(serieId, serie);
	}

	@Transactional
	public void desativarSerie(Long serieId) {
		log.debug("Iniciando o processo de desativação da Série de ID: {}", serieId);
		Serie serie = buscaOuFalha(serieId);
		serie.setAtivo(false);
		atualizar(serieId, serie);
	}

	@Transactional
	public Serie buscaOuFalha(Long serieId) {
		log.debug("Iniciando a busca para o ID:{}", serieId);

		return repository.findById(serieId).orElseThrow(() -> {
			log.warn("Falha na consulta: Série com ID {} não existe no seu sistema", serieId);
			return new SerieNaoEncontradaException(serieId);
		});
	}

	@Transactional
	public Serie salvar(Serie serie) {
		log.debug("Iniciando processo para salvar a serie: '{}'", serie.getTitulo());
		Long categoriaId = Optional.ofNullable(serie).map(Serie::getCategoria).map(Categoria::getId).orElse(null);

		if (categoriaId == null) {
			return null;
		}

		log.debug("Validando a existência da categoria com ID: {}", categoriaId);
		Categoria categoria = categoriaService.buscaOuFalha(categoriaId);

		serie.setCategoria(categoria);

		Serie serieSalva = repository.save(serie);
		log.info("Série '{}' salva com sucesso! ID gerado no banco: {}", serieSalva.getTitulo(), serieSalva.getId());
		return serieSalva;
	}

	@Transactional
	public void deletar(Long serieId) {
		log.debug("Iniciando o processo de exclusão para a Série de ID: {}", serieId);
		Serie serie = repository.findById(serieId).orElseThrow(() -> new SerieNaoEncontradaException(serieId));

		repository.deleteById(serie.getId());
		log.info("Série deletada com sucesso!");
	}

	@Transactional
	public Serie atualizar(Long serieId, Serie serie) {
		log.debug("Iniciando processo para atualização do recurso da Série '{}' de ID: {}", serie.getTitulo(), serieId);
		Serie serieAtual = buscaOuFalha(serieId);

		requestMapper.updateEntity(serie, serieAtual);

		log.info("Série '{}' atualizada com sucesso!", serieAtual.getTitulo());

		serieAtual = salvar(serieAtual);
		return serieAtual;
	}

	public List<Serie> listar() {
		log.debug("Acessando o repositório para buscar as séries ordenadas por ID.");
		List<Serie> series = repository.findAllByOrderByIdAsc();
		log.info("Busca de séries finalizada. Total de registros encontrados: {}", series.size());
		return series;
	}


//	public Serie toDomain(SerieRequestDto serieRequestDto) {
//	Serie serie = new Serie();
//	Categoria categoria = categoriaService.buscaOuFalha(serieRequestDto.getCategoriaId().getId());
//
//	 forma manual
//	SerieResponseDto dto = new SerieResponseDto(serieAtual.getId(), serieAtual.getTitle(),
//			serieAtual.getTotalSeasons(), serieAtual.getImdbRating(), serieAtual.getActors(),
//			serieAtual.getPoster(), serieAtual.getPlot());
//	serie.setCategoria(categoria);
//	BeanUtils.copyProperties(serieRequestDto, serie);
//
//	return serie;
//}

//	@Transactional
//	public List<SerieResponseDto> toDtoList(List<Serie> series) {
//		 forma manual
//		List<SerieResponseDto> listDto = series.stream().map(s -> new SerieResponseDto(s.getId(), s.getTitle(),
//				s.getTotalSeasons(), s.getImdbRating(), s.getActors(), s.getPoster(), s.getPlot())).toList();
//
//		List<SerieResponseDto> listDto = series.stream().map(s -> {
//
//			SerieResponseDto dto = new SerieResponseDto();
//			Categoria categoria = s.getCategoria();
//
//			if (s.getCategoria() != null) {
//				CategoriaResponseDto categoriaDto = categoriaService.toDto(categoria);
//				dto.setCategoriaId(categoriaDto);
//			}
//
//			BeanUtils.copyProperties(s, dto);
//			return dto;
//
//		}).toList();
//
//		return listDto;
//	}

//	public SerieResponseDto toDto(Serie serie) {
//
//		SerieResponseDto dto = new SerieResponseDto();
//
//		Categoria categoria = serie.getCategoria();
//		CategoriaResponseDto categoriaResponseDto = categoriaService.toDto(categoria);
//
//		 forma manual
//		SerieResponseDto dto = new SerieResponseDto(serieAtual.getId(), serieAtual.getTitle(),
//				serieAtual.getTotalSeasons(), serieAtual.getImdbRating(), serieAtual.getActors(),
//				serieAtual.getPoster(), serieAtual.getPlot());
//
//		BeanUtils.copyProperties(serie, dto, "series");
//		dto.setCategoriaId(categoriaResponseDto);
//		return dto;
//	}

}
