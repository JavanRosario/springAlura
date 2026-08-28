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
import com.springAlura.springAlura.api.dto2.SerieRequestDto;
import com.springAlura.springAlura.api.dto2.SerieResponseDto;
import com.springAlura.springAlura.domain.model.Serie;
import com.springAlura.springAlura.domain.model.SerieFiltroDto;
import com.springAlura.springAlura.domain.repositories.SerieRepository;
import com.springAlura.springAlura.domain.service.SerieService;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@RestController
@Data
@RequestMapping(PathsApi.MAIN_PATH)
@Slf4j
public class SerieController {

	@Autowired
	SerieService serieService;

	@Autowired
	SerieRepository repository;

	@GetMapping("/filtros")
	public List<SerieResponseDto> listarComFiltros(@ModelAttribute SerieFiltroDto filtros) {

		return serieService
				.toDtoList(serieService.buscaComFiltros(filtros.titulo(), filtros.notaMax(), filtros.limite()));
	}

	@GetMapping()
	public List<SerieResponseDto> listar() {
		log.info("Recebida a requisição GET para listar Séries");
		return serieService.toDtoList(serieService.listar());
	}

	@GetMapping("/{serieId}")
	public SerieResponseDto listarPorId(@PathVariable Long serieId) {
		log.info("Recebida a requsição GET para mostrar uma série");
		return serieService.toDto(serieService.buscaOuFalha(serieId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public SerieResponseDto salvar(@RequestBody @Valid SerieRequestDto serieRequestDto) {
		log.info("Recebida a requsição POST para cadastrar uma série");
		Serie serieAtual = serieService.toDomain(serieRequestDto);
		serieAtual = serieService.salvar(serieAtual);
		return serieService.toDto(serieAtual);
	}

	@PutMapping("/{serieId}")
	public ResponseEntity<SerieResponseDto> atualizar(@PathVariable Long serieId,
			@RequestBody @Valid SerieRequestDto serieRequestDto) {
		log.info("Recebida a requsição PUT para atualizar uma série");
		Serie serieAtual = serieService.toDomain(serieRequestDto);
		serieAtual = serieService.atualizar(serieId, serieAtual);

		return ResponseEntity.status(HttpStatus.CREATED).body(serieService.toDto(serieAtual));
	}

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
