package com.springAlura.springAlura.domain.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.envers.AuditReader;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.springAlura.springAlura.api.dto.SerieFiltroRequestDto;
import com.springAlura.springAlura.api.dto2.CategoriaResponseDto;
import com.springAlura.springAlura.api.dto2.SerieRequestDto;
import com.springAlura.springAlura.api.dto2.SerieResponseDto;
import com.springAlura.springAlura.api.especification.SerieEspecification;
import com.springAlura.springAlura.domain.exception.SerieNaoEncontradaException;
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

//	public List<SerieAuditoriaDto> listarHistoricoSerie(Long serieId) {
//
//		LocalDateTime dataCriacaoOriginal = null;
//
//		AuditReader auditReader = AuditReaderFactory.get(entityManager);
//
//		@SuppressWarnings("unchecked")
//		List<Object[]> rawResults = auditReader.createQuery().forRevisionsOfEntityWithChanges(Serie.class, true)
//				.add(AuditEntity.id().eq(serieId)).getResultList();
//
//		return rawResults.stream().map(r -> {
//
//			Serie serie = (Serie) r[0];
//			AuditRevisionEntity revision = (AuditRevisionEntity) r[1];
//			RevisionType type = (RevisionType) r[2];
//
//			LocalDateTime dataDestaRevisao = revision.getRevisionDate().toInstant().atZone(ZoneId.systemDefault())
//					.toLocalDateTime();
//
//			if (dataCriacaoOriginal == null || type == RevisionType.ADD) {
//				dataCriacaoOriginal = dataDestaRevisao;
//			}
//
//			@SuppressWarnings("unchecked")
//			Set<String> propertiesChanged = (Set<String>) r[3];
//
//			String usuarioAuditado = (revision.getUser() != null) ? revision.getUser() : "Sistema_Sem_Login";
//
//			return new SerieAuditoriaDto(serie.getId(), serie.getTitulo(), revision.getId(), usuarioAuditado,
//					type.name(), dataDestaRevisao, dataCriacaoOriginal, propertiesChanged.stream().toList());
//
//		}).toList();
//	}

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
		return paginas.map(s -> toDto(s));
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

	@Transactional
	public Page<SerieResponseDto> toDtoListPage(Page<Serie> page) {
		// forma manual
//		List<SerieResponseDto> listDto = series.stream().map(s -> new SerieResponseDto(s.getId(), s.getTitle(),
//				s.getTotalSeasons(), s.getImdbRating(), s.getActors(), s.getPoster(), s.getPlot())).toList();

		List<SerieResponseDto> listDto = page.getContent().stream().map(s -> {

			SerieResponseDto dto = new SerieResponseDto();
			Categoria categoria = s.getCategoria();

			if (s.getCategoria() != null) {
				CategoriaResponseDto categoriaDto = categoriaService.toDto(categoria);
				dto.setCategoriaId(categoriaDto);
			}

			BeanUtils.copyProperties(s, dto);
			return dto;

		}).toList();

		Page<SerieResponseDto> pagina = new PageImpl<>(listDto);

		return pagina;
	}

	public List<Serie> listar() {
		log.debug("Acessando o repositório para buscar as séries ordenadas por ID.");
		List<Serie> series = repository.findAllByOrderByIdAsc();
		log.info("Busca de séries finalizada. Total de registros encontrados: {}", series.size());
		return series;
	}

}
