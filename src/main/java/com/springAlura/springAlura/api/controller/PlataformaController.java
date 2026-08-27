package com.springAlura.springAlura.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.springAlura.springAlura.api.dto2.PlataformaRequestDto;
import com.springAlura.springAlura.api.dto2.PlataformaResponseDto;
import com.springAlura.springAlura.domain.model.Plataforma;
import com.springAlura.springAlura.domain.service.PlataformaService;

import lombok.Data;

@RestController
@RequestMapping("/plataformas")
@Data
public class PlataformaController {

	@Autowired
	PlataformaService plataformaService;

	@GetMapping
	public List<PlataformaResponseDto> listar() {
		return plataformaService.toDtoList(plataformaService.listar());
	}

	@GetMapping("/{plataformaId}")
	public PlataformaResponseDto buscarPorId(@PathVariable Long plataformaId) {
		return plataformaService.toDto(plataformaService.buscaOuFalha(plataformaId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PlataformaResponseDto salvar(@RequestBody PlataformaRequestDto dto) {
		Plataforma plataforma = plataformaService.toDomain(dto);
		plataforma = plataformaService.salvar(plataforma);
		return plataformaService.toDto(plataforma);
	}

	@PutMapping("/{plataformaId}")
	public PlataformaResponseDto atualizar(@PathVariable Long plataformaId, @RequestBody PlataformaRequestDto dto) {
		return plataformaService.toDto(plataformaService.atualizar(plataformaId, dto));
	}

	@DeleteMapping("/{plataformaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(Long plataformaId) {
		plataformaService.deletar(plataformaId);
	}

}
