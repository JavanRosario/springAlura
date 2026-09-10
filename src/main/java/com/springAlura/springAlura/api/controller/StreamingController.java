package com.springAlura.springAlura.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springAlura.springAlura.api.controller2.StreamingsApi;
import com.springAlura.springAlura.api.docs.PathsApi;
import com.springAlura.springAlura.api.dto2.StreamingRequestDto;
import com.springAlura.springAlura.api.dto2.StreamingResponseDto;
import com.springAlura.springAlura.api.mapper.Streaming.StreamingRequestMapper;
import com.springAlura.springAlura.api.mapper.Streaming.StreamingResponseMapper;
import com.springAlura.springAlura.domain.model.Streaming;
import com.springAlura.springAlura.domain.service.StreamingService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.Data;

@RestController
@RequestMapping(PathsApi.MAIN_PATH)
@Data
@SecurityRequirement(name = "bearerAuth")
public class StreamingController implements StreamingsApi {

	@Autowired
	StreamingService streamingService;

	@Autowired
	StreamingResponseMapper responseMapper;
	@Autowired
	StreamingRequestMapper requestMapper;

//	@GetMapping
//	public List<StreamingResponseDto> listar() {
//		return streamingService.toDtoList(streamingService.listar());
//	}
//
//	@GetMapping("{streamingId}")
//	public StreamingResponseDto buscarPorId(@PathVariable Long streamingId) {
//		return streamingService.toDto(streamingService.buscaOuFalha(streamingId));
//	}
//
//	@PostMapping
//	@ResponseStatus(HttpStatus.CREATED)
//	public StreamingResponseDto salvar(@RequestBody @Valid StreamingRequestDto streamingRequestDto) {
//		System.out.println(streamingRequestDto);
//		Streaming streaming = streamingService.toDomain(streamingRequestDto);
//		return streamingService.toDto(streamingService.salvar(streaming));
//	}
//
//	@PutMapping("{streamingId}")
//	public StreamingResponseDto atualizar(@PathVariable Long streamingId,
//			@RequestBody @Valid StreamingRequestDto streamingRequestDto) {
//		Streaming streaming = streamingService.atualizar(streamingId, streamingRequestDto);
//		return streamingService.toDto(streaming);
//	}
//
//	@DeleteMapping("{streamingId}")
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void apagar(@PathVariable Long streamingId) {
//		streamingService.apagar(streamingId);
//	}

	@Override
	public ResponseEntity<Void> apagarStreaming(Long streamingId) {
		streamingService.apagar(streamingId);
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<StreamingResponseDto> atualizarStreaming(Long streamingId,
			@Valid StreamingRequestDto streamingRequestDto) {
		Streaming streaming = streamingService.atualizar(streamingId, streamingRequestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(responseMapper.toDto(streaming));
	}

	@Override
	public ResponseEntity<StreamingResponseDto> buscarStreamingPorId(Long streamingId) {
		Streaming streaming = streamingService.buscaOuFalha(streamingId);
		return ResponseEntity.ok(responseMapper.toDto(streaming));
	}

	@Override
	public ResponseEntity<List<StreamingResponseDto>> listarStreamings() {
		List<StreamingResponseDto> dtos = responseMapper.toDtoList(streamingService.listar());
		return ResponseEntity.ok(dtos);
	}

	@Override
	public ResponseEntity<StreamingResponseDto> salvarStreaming(
			@Valid @RequestBody StreamingRequestDto streamingRequestDto) {
		Streaming streamingAtual = requestMapper.toDomain(streamingRequestDto);
		System.out.println(streamingAtual);
		streamingAtual = streamingService.salvar(streamingAtual);
		return ResponseEntity.ok(responseMapper.toDto(streamingAtual));
	}

}
