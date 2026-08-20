package com.springAlura.springAlura.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.springAlura.springAlura.api.docs.PathsApi;
import com.springAlura.springAlura.api.docs.SwaggerDocControllers;
import com.springAlura.springAlura.api.dto2.SerieRequestDto;
import com.springAlura.springAlura.api.dto2.SerieResponseDto;
import com.springAlura.springAlura.domain.model.Serie;
import com.springAlura.springAlura.domain.model.SerieFiltroDto;
import com.springAlura.springAlura.domain.repositories.SerieRepository;
import com.springAlura.springAlura.domain.service.SerieService;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import lombok.Data;

@RestController
@Data
@RequestMapping(PathsApi.MAIN_PATH)
public class SerieController implements SwaggerDocControllers {

	@Autowired
	SerieService serieService;

	@Autowired
	SerieRepository repository;

	@Override()
	@GetMapping("/filtros")
	public List<SerieResponseDto> listarComFiltros(@ModelAttribute SerieFiltroDto filtros) {
		return serieService
				.toDtoList(serieService.buscaComFiltros(filtros.titulo(), filtros.notaMax(), filtros.limite()));
	}

	@GetMapping()
	@Override()
	public List<SerieResponseDto> listar() {
		return serieService.toDtoList(serieService.listar());
	}

	@GetMapping("/teste")
	public List<Serie> listarTeste() {
		return repository.findAll();
	}

	@Hidden
	@GetMapping("/top5")
	public List<SerieResponseDto> listarTop5() {
		return serieService.toDtoList(serieService.listarTop5());
	}

	@Hidden
	@GetMapping("/lancamentos")
	public List<SerieResponseDto> listarLancamentos() {
		return serieService.toDtoList(serieService.listarLancamentos());
	}

//	@Hidden
//	@GetMapping("/{id}/temporadas/todas")
//	public List<EpisodioResponseDto> temporadas(@PathVariable Long id) {
//		Serie serieEncontrada = serieService.buscaOuFalha(id);
//
//		return serieEncontrada.getEpisodios().stream()
//				.map(e -> new EpisodioResponseDto(e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo())).toList();
//
//	}

//	@Hidden
//	@GetMapping("/{serieId}/temporadas/{temporadaId}")
//	public List<EpisodioResponseDto> temporadaUnica(@PathVariable Long serieId, @PathVariable Long temporadaId) {
//		return episodioService.toDtoList(episodioRepositoryy.temporadaUnica(serieId, temporadaId));
//
//	}

	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public SerieResponseDto salvar(@RequestBody @Valid SerieRequestDto serieRequestDto) {
		Serie serieAtual = serieService.toDomain(serieRequestDto);
		serieAtual = serieService.salvar(serieAtual);
		return serieService.toDto(serieAtual);
	}

	@Override
	@PutMapping("/{serieId}")
	public ResponseEntity<SerieResponseDto> atualizar(@PathVariable Long serieId,
			@RequestBody @Valid SerieRequestDto serieRequestDto) {
		Serie serieAtual = serieService.toDomain(serieRequestDto);
		serieAtual = serieService.atualizar(serieId, serieAtual);

		return ResponseEntity.status(HttpStatus.CREATED).body(serieService.toDto(serieAtual));
	}

	@Override
	@DeleteMapping(PathsApi.ID_SERIE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long serieId) {
		serieService.deletar(serieId);
	}

	@PutMapping("/{serieId}/ativar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarSerie(@PathVariable Long serieId) {
		serieService.ativarSerie(serieId);
	}

	@DeleteMapping("/{serieId}/desativar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desativarSerie(@PathVariable Long serieId) {
		serieService.desativarSerie(serieId);
	}
}
