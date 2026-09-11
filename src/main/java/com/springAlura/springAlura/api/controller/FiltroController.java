package com.springAlura.springAlura.api.controller;

import com.springAlura.springAlura.api.docs.SwaggerSerieController;
import com.springAlura.springAlura.api.dto.SerieFiltroRequestDto;
import com.springAlura.springAlura.api.dto2.SerieResponseDto;
import com.springAlura.springAlura.domain.service.SerieService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/filtros")
@RestController
@Slf4j
@SecurityRequirement(name = "bearerAuth")
public class FiltroController implements SwaggerSerieController {

    @Autowired
    SerieService serieService;

    @GetMapping("/series")
    public Page<SerieResponseDto> listarComFiltros(SerieFiltroRequestDto filtros, Pageable pageable) {
        log.info("Recebida a requisição GET para listar Séries com filtros");
        return serieService.buscaComFiltros(filtros, pageable);
    }
}
