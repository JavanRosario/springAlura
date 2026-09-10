package com.springAlura.springAlura.api.mapper.Streaming;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.springAlura.springAlura.api.dto2.StreamingRequestDto;
import com.springAlura.springAlura.api.dto2.StreamingResponseDto;
import com.springAlura.springAlura.domain.model.Streaming;

@Mapper(componentModel = "spring")
public interface StreamingResponseMapper {

	StreamingResponseDto toDto(Streaming streaming);

	List<StreamingResponseDto> toDtoList(List<Streaming> streamings);

	;

}
