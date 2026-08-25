package com.springAlura.springAlura.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.springAlura.springAlura.api.dto2.StreamingRequestDto;
import com.springAlura.springAlura.api.dto2.StreamingResponseDto;
import com.springAlura.springAlura.domain.model.Streaming;
import com.springAlura.springAlura.domain.service.StreamingService;

import lombok.Data;

@RestController
@RequestMapping("/streamings")
@Data
public class StreamingController {

	@Autowired
	StreamingService streamingService;

	@GetMapping
	public List<StreamingResponseDto> listar() {
		return streamingService.toDtoList(streamingService.listar());
	}

	@GetMapping("{streamingId}")
	public StreamingResponseDto buscarPorId(@PathVariable long streamingId) {
		return streamingService.toDto(streamingService.buscaOuFalha(streamingId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public StreamingResponseDto salvar(StreamingRequestDto streamingRequestDto) {
		Streaming streaming = streamingService.toDomain(streamingRequestDto);
		return streamingService.toDto(streamingService.salvar(streaming));

	}

	@PutMapping("{streamingId}")
	public StreamingResponseDto atualizar(@PathVariable Long streamingId, StreamingRequestDto streamingRequestDto) {
		Streaming streaming = streamingService.toDomain(streamingRequestDto);
		streaming = streamingService.salvar(streaming);
		return streamingService.toDto(streaming);
	}

	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void apagar(Long streamingId) {
		streamingService.apagar(streamingId);
	}

}
