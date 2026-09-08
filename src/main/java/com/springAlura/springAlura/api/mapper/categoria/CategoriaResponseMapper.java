package com.springAlura.springAlura.api.mapper.categoria;

import java.util.List;

import org.mapstruct.Mapper;

import com.springAlura.springAlura.api.dto2.CategoriaResponseDto;
import com.springAlura.springAlura.domain.model.Categoria;

@Mapper(componentModel = "spring")
public interface CategoriaResponseMapper {
	Categoria toDomain(CategoriaResponseDto dto);

	List<CategoriaResponseDto> toDtoList(List<Categoria> objs);

	CategoriaResponseDto toDto(Categoria obj);
	
	
}
