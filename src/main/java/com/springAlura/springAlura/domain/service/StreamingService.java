package com.springAlura.springAlura.domain.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springAlura.springAlura.api.dto2.PlataformaResponseDto;
import com.springAlura.springAlura.api.dto2.StreamingResponseDto;
import com.springAlura.springAlura.api.dto2.UsuarioResponseDto;
import com.springAlura.springAlura.domain.model.Streaming;

import jakarta.transaction.Transactional;

@Service
public class StreamingService {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private PlataformaService plataformaService;

	@Transactional
	public List<StreamingResponseDto> toDtoList(List<Streaming> streamings) {
		// forma manual
//		List<SerieResponseDto> listDto = series.stream().map(s -> new SerieResponseDto(s.getId(), s.getTitle(),
//				s.getTotalSeasons(), s.getImdbRating(), s.getActors(), s.getPoster(), s.getPlot())).toList();

		List<StreamingResponseDto> listDto = streamings.stream().map(s -> {
			StreamingResponseDto dto = new StreamingResponseDto();
			UsuarioResponseDto usuarioDto = usuarioService.toDto(s.getUsuario());
			PlataformaResponseDto plataformaDto = plataformaService.toDto(s.getPlataforma());

			BeanUtils.copyProperties(s, dto);
			dto.setUsuario(usuarioDto);
			dto.setPlataforma(plataformaDto);
			return dto;
		}).toList();

		return listDto;
	}
}
