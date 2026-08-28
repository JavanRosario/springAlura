package com.springAlura.springAlura.domain.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springAlura.springAlura.api.dto2.PlataformaRequestDto;
import com.springAlura.springAlura.api.dto2.PlataformaResponseDto;
import com.springAlura.springAlura.domain.exception.SerieNaoEncontradaException;
import com.springAlura.springAlura.domain.model.Plataforma;
import com.springAlura.springAlura.domain.repositories.PlataformaRepository;

import jakarta.transaction.Transactional;

@Service
public class PlataformaService {

	@Autowired
	PlataformaRepository plataformaRepository;

	public List<Plataforma> listar() {
		return plataformaRepository.findAll();
	}

	public Plataforma buscaOuFalha(Long plataformaId) {
		return plataformaRepository.findById(plataformaId)
				.orElseThrow(() -> new SerieNaoEncontradaException(plataformaId));
	}

	public PlataformaResponseDto toDto(Plataforma plataforma) {
		PlataformaResponseDto dto = new PlataformaResponseDto();

		BeanUtils.copyProperties(plataforma, dto);

		return dto;
	}

	public List<PlataformaResponseDto> toDtoList(List<Plataforma> plataformas) {
		List<PlataformaResponseDto> dtos = plataformas.stream().map(s -> {
			PlataformaResponseDto plataformaDto = new PlataformaResponseDto();

			BeanUtils.copyProperties(s, plataformaDto);

			return plataformaDto;
		}).toList();

		return dtos;
	}

	public Plataforma toDomain(PlataformaRequestDto dto) {
		Plataforma plataforma = new Plataforma();

		// forma manual
//		SerieResponseDto dto = new SerieResponseDto(serieAtual.getId(), serieAtual.getTitle(),
//				serieAtual.getTotalSeasons(), serieAtual.getImdbRating(), serieAtual.getActors(),
//				serieAtual.getPoster(), serieAtual.getPlot());

		BeanUtils.copyProperties(dto, plataforma);
		return plataforma;
	}

	@Transactional
	public Plataforma salvar(Plataforma plataforma) {
		return plataformaRepository.save(plataforma);
	}

	@Transactional
	public Plataforma atualizar(Long plataformaId, PlataformaRequestDto plataforma) {
		Plataforma objetoAtual = buscaOuFalha(plataformaId);

		BeanUtils.copyProperties(plataforma, objetoAtual);

		return salvar(objetoAtual);
	}

	@Transactional
	public void deletar(Long plataformaId) {
		Plataforma plataforma = buscaOuFalha(plataformaId);
		plataformaRepository.delete(plataforma);
	}
}
