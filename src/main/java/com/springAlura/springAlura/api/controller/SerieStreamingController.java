package com.springAlura.springAlura.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.springAlura.springAlura.api.dto2.StreamingResponseDto;
import com.springAlura.springAlura.domain.model.Serie;
import com.springAlura.springAlura.domain.service.SerieService;
import com.springAlura.springAlura.domain.service.StreamingService;

@RestController
@RequestMapping("series/{serieId}/streamings")
public class SerieStreamingController {

	@Autowired
	SerieService serieService;

	@Autowired
	StreamingService streamingService;

	@GetMapping
	public List<StreamingResponseDto> listar(@PathVariable Long serieId) {
		Serie serie = serieService.buscaOuFalha(serieId);
		return streamingService.toDtoList(serie.getStreaming());
	}

	@PutMapping("/{streamingId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associarSerieStreaming(@PathVariable Long serieId, @PathVariable Long streamingId) {
		serieService.associarStreaming(serieId, streamingId);
	}
}
