package com.springAlura.springAlura.domain.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.springAlura.springAlura.api.dto2.StreamingDto;
import com.springAlura.springAlura.domain.model.Streaming;

import jakarta.transaction.Transactional;

@Service
public class StreamingService {

	@Transactional
	public List<StreamingDto> toDtoList(List<Streaming> streamings) {
		// forma manual
//		List<SerieResponseDto> listDto = series.stream().map(s -> new SerieResponseDto(s.getId(), s.getTitle(),
//				s.getTotalSeasons(), s.getImdbRating(), s.getActors(), s.getPoster(), s.getPlot())).toList();

		List<StreamingDto> listDto = streamings.stream().map(s -> {
			StreamingDto dto = new StreamingDto();
			BeanUtils.copyProperties(s, dto);
			return dto;
		}).toList();

		return listDto;
	}
}
