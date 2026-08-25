package com.springAlura.springAlura.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springAlura.springAlura.api.dto2.PlataformaResponseDto;
import com.springAlura.springAlura.api.dto2.UsuarioResponseDto;
import com.springAlura.springAlura.domain.exception.SerieNaoEncontradaException;
import com.springAlura.springAlura.domain.model.Plataforma;
import com.springAlura.springAlura.domain.model.Usuario;
import com.springAlura.springAlura.domain.repositories.PlataformaRepository;

@Service
public class PlataformaService {

	@Autowired
	PlataformaRepository plataformaRepository;

	public Plataforma buscaOuFalha(Long plataformaId) {
		return plataformaRepository.findById(plataformaId)
				.orElseThrow(() -> new SerieNaoEncontradaException(plataformaId));
	}

	public PlataformaResponseDto toDto(Plataforma plataforma) {
		PlataformaResponseDto dto = new PlataformaResponseDto();
		
		BeanUtils.copyProperties(plataforma, dto);
		
		return dto;
	}
	
	public Plataforma toDomain(PlataformaResponseDto plataformaResponseDto) {
		Plataforma plataforma = new Plataforma();

		// forma manual
//		SerieResponseDto dto = new SerieResponseDto(serieAtual.getId(), serieAtual.getTitle(),
//				serieAtual.getTotalSeasons(), serieAtual.getImdbRating(), serieAtual.getActors(),
//				serieAtual.getPoster(), serieAtual.getPlot());

		BeanUtils.copyProperties(plataformaResponseDto, plataforma);
		return plataforma;
	}
}
