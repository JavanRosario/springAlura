package com.springAlura.springAlura.api.controller;

import com.springAlura.springAlura.api.controller2.SeriesStreamingsApi;
import com.springAlura.springAlura.api.docs.PathsApi;
import com.springAlura.springAlura.api.dto2.StreamingResponseDto;
import com.springAlura.springAlura.api.mapper.Streaming.StreamingResponseMapper;
import com.springAlura.springAlura.domain.model.Serie;
import com.springAlura.springAlura.domain.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(PathsApi.MAIN_PATH)
public class SerieStreamingController implements SeriesStreamingsApi {

    @Autowired
    SerieService serieService;

    @Autowired
    StreamingResponseMapper responseMapper;

    @Override
    public ResponseEntity<Void> associarSerieAoStreaming(Long serieId, Long streamingId) {
        serieService.associarStreaming(serieId, streamingId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<StreamingResponseDto>> listarStreamingsDaSerie(Long serieId) {
        Serie serie = serieService.buscaOuFalha(serieId);
        List<StreamingResponseDto> streamingResponseDtoList = responseMapper.toDtoList(serie.getStreaming());
        return ResponseEntity.ok(streamingResponseDtoList);
    }
}
