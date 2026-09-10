package com.springAlura.springAlura.api.mapper.serie;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.springAlura.springAlura.api.dto2.SerieRequestDto;
import com.springAlura.springAlura.api.dto2.StreamingRequestDto;
import com.springAlura.springAlura.domain.model.Serie;
import com.springAlura.springAlura.domain.model.Streaming;

@Mapper(componentModel = "spring")
public interface SerieRequestMapper {

	Serie toDomain(SerieRequestDto dto);

	@Mapping(target = "id", ignore = true)
	void updateEntity(Serie serie, @MappingTarget Serie serieBuscadaNoBanco);

	

}
