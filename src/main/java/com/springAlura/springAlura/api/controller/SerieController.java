package com.springAlura.springAlura.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.springAlura.springAlura.api.docs.PathsApi;
import com.springAlura.springAlura.api.docs.SwaggerSerieController;
import com.springAlura.springAlura.api.dto.SerieFiltroRequestDto;
import com.springAlura.springAlura.api.dto2.SerieRequestDto;
import com.springAlura.springAlura.api.dto2.SerieResponseDto;
import com.springAlura.springAlura.domain.model.Serie;
import com.springAlura.springAlura.domain.repositories.SerieRepository;
import com.springAlura.springAlura.domain.service.SerieService;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@RestController
@Data
@RequestMapping(PathsApi.MAIN_PATH)
@Slf4j
public class SerieController implements SwaggerSerieController {

	@Autowired
	SerieService serieService;

	@Autowired
	SerieRepository repository;
//
//	@GetMapping("/{serieId}/historicos")
//	public List<SerieAuditoriaDto> listarHistorico(@PathVariable Long serieId) {
//		return serieService.listarHistoricoSerie(serieId);
//	}

	@GetMapping("/filtros")
	public Page<SerieResponseDto> listarComFiltros(SerieFiltroRequestDto filtros, Pageable pageable) {
		log.info("Recebida a requisição GET para listar Séries com filtros");
		return serieService.buscaComFiltros(filtros, pageable);
	}

	@GetMapping()
	@Override
	public List<SerieResponseDto> listar() {
		log.info("Recebida a requisição GET para listar Séries");
		return serieService.toDtoList(serieService.listar());
	}

	@Override
	@GetMapping(PathsApi.ID_SERIE)
	public SerieResponseDto listarPorId(@PathVariable Long serieId) {
		log.info("Recebida a requsição GET para mostrar uma série");
		return serieService.toDto(serieService.buscaOuFalha(serieId));
	}

	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public SerieResponseDto salvar(@RequestBody @Valid SerieRequestDto serieRequestDto) {
		log.info("Recebida a requsição POST para cadastrar uma série");
		Serie serieAtual = serieService.toDomain(serieRequestDto);
		serieAtual = serieService.salvar(serieAtual);
		return serieService.toDto(serieAtual);
	}

	@Override
	@PutMapping("/{serieId}")
	public ResponseEntity<SerieResponseDto> atualizar(@PathVariable Long serieId,
			@RequestBody @Valid SerieRequestDto serieRequestDto) {
		log.info("Recebida a requsição PUT para atualizar uma série");
		Serie serieAtual = serieService.toDomain(serieRequestDto);
		serieAtual = serieService.atualizar(serieId, serieAtual);

		return ResponseEntity.status(HttpStatus.CREATED).body(serieService.toDto(serieAtual));
	}

	@Override
	@DeleteMapping(PathsApi.ID_SERIE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long serieId) {
		log.info("Recebida a requsição DELETE para exclusão de uma série");
		serieService.deletar(serieId);
	}

	@Override
	@PutMapping(PathsApi.ATIVANDO_SERIE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarSerie(@PathVariable Long serieId) {
		log.info("Recebida a requsição {} para ativação de uma série", PathsApi.ATIVANDO_SERIE);
		serieService.ativarSerie(serieId);
	}

	@Override
	@DeleteMapping(PathsApi.DESATIVANDO_SERIE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desativarSerie(@PathVariable Long serieId) {
		log.info("Recebida a requsição {} para desativação de uma série", PathsApi.DESATIVANDO_SERIE);
		serieService.desativarSerie(serieId);
	}
}
