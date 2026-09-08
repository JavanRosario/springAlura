package com.springAlura.springAlura.api.mapper.serie;

import org.mapstruct.Mapper;

import com.springAlura.springAlura.api.dto2.CategoriaIdRequestDto;
import com.springAlura.springAlura.domain.model.Categoria;

@Mapper(componentModel = "spring")
public interface SerieRequestMapper {

	Categoria toDomain(CategoriaIdRequestDto id);

}
