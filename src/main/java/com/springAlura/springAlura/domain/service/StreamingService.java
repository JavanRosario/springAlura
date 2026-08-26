package com.springAlura.springAlura.domain.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springAlura.springAlura.api.dto2.PlataformaResponseDto;
import com.springAlura.springAlura.api.dto2.StreamingRequestDto;
import com.springAlura.springAlura.api.dto2.StreamingResponseDto;
import com.springAlura.springAlura.api.dto2.UsuarioResponseDto;
import com.springAlura.springAlura.domain.exception.SerieNaoEncontradaException;
import com.springAlura.springAlura.domain.model.Plataforma;
import com.springAlura.springAlura.domain.model.Streaming;
import com.springAlura.springAlura.domain.model.Usuario;
import com.springAlura.springAlura.domain.repositories.StreamingRepository;

import jakarta.transaction.Transactional;

@Service
public class StreamingService {

	@Autowired
	StreamingRepository repository;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private PlataformaService plataformaService;

	@Transactional
	public Streaming salvar(Streaming streaming) {
		Long usuarioId = streaming.getUsuario().getId();
		Usuario usuario = usuarioService.buscaOuFalha(usuarioId);
		streaming.setUsuario(usuario);
		return repository.save(streaming);
	}

	public Streaming buscaOuFalha(Long streamingId) {
		return repository.findById(streamingId).orElseThrow(() -> new SerieNaoEncontradaException(streamingId));
	}

	public List<Streaming> listar() {
		return repository.findAll();
	}

	public StreamingResponseDto toDto(Streaming streaming) {
		StreamingResponseDto streamingResponseDto = new StreamingResponseDto();

		Long usuarioId = streaming.getUsuario().getId();
		Usuario usuario = usuarioService.buscaOuFalha(usuarioId);

		Long plataformaId = streaming.getPlataforma().getId();
		Plataforma plataforma = plataformaService.buscaOuFalha(plataformaId);

		BeanUtils.copyProperties(streaming, streamingResponseDto);
		streamingResponseDto.setUsuario(usuarioService.toDto(usuario));
		streamingResponseDto.setPlataforma(plataformaService.toDto(plataforma));
		return streamingResponseDto;

	}

	public Streaming toDomain(StreamingRequestDto streamingRequestDto) {
		Streaming streaming = new Streaming();

		Long usuarioId = streamingRequestDto.getUsuarioId().getId();
		Usuario usuario = usuarioService.buscaOuFalha(usuarioId);

		Long plataformaId = streamingRequestDto.getPlataformaId().getId();
		Plataforma plataforma = plataformaService.buscaOuFalha(plataformaId);

		BeanUtils.copyProperties(streamingRequestDto, streaming);
		streaming.setUsuario(usuario);
		streaming.setPlataforma(plataforma);
		return streaming;

	}

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

	public List<Streaming> toDomainList(List<StreamingRequestDto> requestDto) {

		List<Streaming> streamings = requestDto.stream().map(s -> {
			Streaming streaming = new Streaming();

			Usuario usuario = usuarioService.buscaOuFalha(s.getUsuarioId().getId());
			Plataforma plataforma = plataformaService.buscaOuFalha(s.getPlataformaId().getId());

			streaming.setUsuario(usuario);
			streaming.setPlataforma(plataforma);
			BeanUtils.copyProperties(s, streaming);

			return streaming;

		}).toList();

		return streamings;
	}

	public Streaming atualizar(Long streamingId, StreamingRequestDto streamingRequestDto) {
		Streaming objetoAtual = buscaOuFalha(streamingId);

		Long usuarioId = streamingRequestDto.getUsuarioId().getId();
		Usuario usuario = usuarioService.buscaOuFalha(usuarioId);

		Long plataformaId = streamingRequestDto.getPlataformaId().getId();
		Plataforma plataforma = plataformaService.buscaOuFalha(plataformaId);

		BeanUtils.copyProperties(streamingRequestDto, objetoAtual);
		objetoAtual.setUsuario(usuario);
		objetoAtual.setPlataforma(plataforma);
		return salvar(objetoAtual);
	}

	public void apagar(Long streamingId) {
		Streaming streaming = buscaOuFalha(streamingId);
		repository.delete(streaming);
	}

}
