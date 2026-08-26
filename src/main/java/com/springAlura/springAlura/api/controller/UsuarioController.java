package com.springAlura.springAlura.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springAlura.springAlura.api.dto2.UsuarioCriacaoRequestDto;
import com.springAlura.springAlura.api.dto2.UsuarioResponseDto;
import com.springAlura.springAlura.domain.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.Data;

@RestController
@RequestMapping("usuarios")
@Data
public class UsuarioController {

	@Autowired
	UsuarioService usuarioService;

	@GetMapping
	public List<UsuarioResponseDto> listar() {
		return usuarioService.toDtoList(usuarioService.listar());
	}

	@GetMapping("/{usuarioId}")
	public UsuarioResponseDto listar(@PathVariable Long usuarioId) {
		return usuarioService.toDto(usuarioService.buscaOuFalha(usuarioId));
	}

	@PostMapping
	public UsuarioResponseDto criarUsuario(@RequestBody @Valid UsuarioCriacaoRequestDto dto) {
		return usuarioService.toDto(usuarioService.salvar(dto));
	}
}
