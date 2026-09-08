package com.springAlura.springAlura.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.OffsetDateTime;
import java.util.List;


public record SerieAuditoriaResponseDto(

        Long id,
        String titulo,
        Integer revisionId,
        String usuario,
        String operacao,

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        OffsetDateTime dataRevisao,

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        OffsetDateTime dataCriacao,

        List<String> camposAlterados

) {
}
