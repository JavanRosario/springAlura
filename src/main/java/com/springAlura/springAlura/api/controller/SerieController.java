package com.springAlura.springAlura.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.springAlura.springAlura.api.docs.SwaggerDocControllers;
import com.springAlura.springAlura.api.dto.request.SerieRequestDto;
import com.springAlura.springAlura.api.dto.response.EpisodioResponseDto;
import com.springAlura.springAlura.api.dto.response.SerieResponseDto;
import com.springAlura.springAlura.domain.model.Serie;
import com.springAlura.springAlura.domain.repositories.EpisodioRepositoryy;
import com.springAlura.springAlura.domain.service.EpisodioService;
import com.springAlura.springAlura.domain.service.OmdbService;
import com.springAlura.springAlura.domain.service.SerieService;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import lombok.Data;

@RestController
@Data
@RequestMapping("/series")
public class SerieController implements SwaggerDocControllers {

	@Autowired
	SerieService serieService;

	@Autowired
	OmdbService omdbService;

	@Autowired
	EpisodioRepositoryy episodioRepositoryy;

	@Autowired
	EpisodioService episodioService;

	@GetMapping()
	@Override()
	@ResponseStatus(code = HttpStatus.OK)
	public List<SerieResponseDto> listar() {
		return serieService.toDtoList(serieService.listar());
	}

	@Hidden
	@GetMapping("/{serieId}")
	public SerieResponseDto listarId(@PathVariable Long serieId) {
		return serieService.toDto(serieService.buscaOuFalha(serieId));
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

	@Hidden
	@GetMapping("/{id}/temporadas/todas")
	public List<EpisodioResponseDto> temporadas(@PathVariable Long id) {
		Serie serieEncontrada = serieService.buscaOuFalha(id);

		return serieEncontrada.getEpisodios().stream()
				.map(e -> new EpisodioResponseDto(e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo())).toList();

	}

	@Hidden
	@GetMapping("/{serieId}/temporadas/{temporadaId}")
	public List<EpisodioResponseDto> temporadaUnica(@PathVariable Long serieId, @PathVariable Long temporadaId) {
		return episodioService.toDtoList(episodioRepositoryy.temporadaUnica(serieId, temporadaId));

	}

	@Override
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
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
	@DeleteMapping("/{serieId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long serieId) {
		serieService.deletar(serieId);
	}
}
