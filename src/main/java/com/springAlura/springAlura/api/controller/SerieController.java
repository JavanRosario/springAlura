package com.springAlura.springAlura.api.controller;

import com.springAlura.springAlura.api.controller2.SeriesApi;
import com.springAlura.springAlura.api.docs.PathsApi;
import com.springAlura.springAlura.api.dto.SerieFiltroRequestDto;
import com.springAlura.springAlura.api.dto2.SerieRequestDto;
import com.springAlura.springAlura.api.dto2.SerieResponseDto;
import com.springAlura.springAlura.api.mapper.serie.SerieRequestMapper;
import com.springAlura.springAlura.api.mapper.serie.SerieResponseMapper;
import com.springAlura.springAlura.domain.model.Serie;
import com.springAlura.springAlura.domain.service.SerieService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Data
@RequestMapping(PathsApi.MAIN_PATH)
@Slf4j
@SecurityRequirement(name = "bearerAuth")
public class SerieController implements SeriesApi{

    @Autowired
    SerieService serieService;

    @Autowired
    private SerieResponseMapper responseMapper;

    @Autowired
    private SerieRequestMapper requestMapper;

//	@GetMapping("/{serieId}/historicos")
//	public List<SerieAuditoriaResponseDto> listarHistorico(@PathVariable Long serieId) {
//		return serieService.listarHistoricoSerie(serieId);
//	}
//

//
//	@GetMapping()
//	@Override
//	public List<SerieResponseDto> listar() {
//		log.info("Recebida a requisição GET para listar Séries");
//		return responseMapper.toDtoList(serieService.listar());
//	}
//
//	@Override
//	@GetMapping(PathsApi.ID_SERIE)
//	public SerieResponseDto listarPorId(@PathVariable Long serieId) {
//		log.info("Recebida a requsição GET para mostrar uma série");
//		return responseMapper.toDto(serieService.buscaOuFalha(serieId));
//	}
//
//	@Override
//	@PostMapping
//	@ResponseStatus(HttpStatus.CREATED)
//	public SerieResponseDto salvar(@RequestBody @Valid SerieRequestDto serieRequestDto) {
//		log.info("Recebida a requsição POST para cadastrar uma série");
//		Serie serieAtual = requestMapper.toDomain(serieRequestDto);
//		serieAtual = serieService.salvar(serieAtual);
//		return responseMapper.toDto(serieAtual);
//	}
//
//	@Override
//	@PutMapping("/{serieId}")
//	public ResponseEntity<SerieResponseDto> atualizar(@PathVariable Long serieId,
//			@RequestBody @Valid SerieRequestDto serieRequestDto) {
//		log.info("Recebida a requsição PUT para atualizar uma série");
//		Serie serieAtual = requestMapper.toDomain(serieRequestDto);
//		serieAtual = serieService.atualizar(serieId, serieAtual);
//		return ResponseEntity.status(HttpStatus.CREATED).body(responseMapper.toDto(serieAtual));
//	}
//
//	@Override
//	@DeleteMapping(PathsApi.ID_SERIE)
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void excluir(@PathVariable Long serieId) {
//		log.info("Recebida a requsição DELETE para exclusão de uma série");
//		serieService.deletar(serieId);
//	}
//
//	@Override
//	@PutMapping(PathsApi.ATIVANDO_SERIE)
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void ativarSerie(@PathVariable Long serieId) {
//		log.info("Recebida a requsição {} para ativação de uma série", PathsApi.ATIVANDO_SERIE);
//		serieService.ativarSerie(serieId);
//	}
//
//	@Override
//	@DeleteMapping(PathsApi.DESATIVANDO_SERIE)
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void desativarSerie(@PathVariable Long serieId) {
//		log.info("Recebida a requsição {} para desativação de uma série", PathsApi.DESATIVANDO_SERIE);
//		serieService.desativarSerie(serieId);
//	}


    @Override
    public ResponseEntity<Void> ativarSerie(Long serieId) {
        serieService.ativarSerie(serieId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<SerieResponseDto> atualizarSerie(Long serieId, @Valid @RequestBody SerieRequestDto serieRequestDto) {
        Serie serie = requestMapper.toDomain(serieRequestDto);
        serieService.atualizar(serieId, serie);
        return ResponseEntity.ok(responseMapper.toDto(serie));
    }

    @Override
    public ResponseEntity<SerieResponseDto> buscarSeriePorId(Long serieId) {
        Serie serie = serieService.buscaOuFalha(serieId);
        return ResponseEntity.ok(responseMapper.toDto(serie));
    }

    @Override
    public ResponseEntity<Void> desativarSerie(Long serieId) {
        serieService.desativarSerie(serieId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> excluirSerie(Long serieId) {
        serieService.deletar(serieId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<SerieResponseDto>> listarSeries() {
        List<SerieResponseDto> dtos = responseMapper.toDtoList(serieService.listar());
        return ResponseEntity.ok(dtos);
    }


    @Override
    public ResponseEntity<SerieResponseDto> salvarSerie(SerieRequestDto serieRequestDto) {
        return SeriesApi.super.salvarSerie(serieRequestDto);
    }


}
