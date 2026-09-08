package com.springAlura.springAlura.api.mapper.serie;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.springAlura.springAlura.api.dto2.SerieResponseDto;
import com.springAlura.springAlura.domain.model.Serie;

@Mapper(componentModel = "spring")
public interface SerieResponseMapper {

	Serie toDomain(SerieResponseDto dto);

	List<SerieResponseDto> toDtoList(List<Serie> series);

	@Mapping(target = "categoriaId", source = "categoria")
	SerieResponseDto toDto(Serie serie);

}
