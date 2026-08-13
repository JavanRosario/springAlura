package com.springAlura.springAlura.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.springAlura.springAlura.model.Serie;
import com.springAlura.springAlura.service.SerieService;

import lombok.Data;

@RestController
@Data
@RequestMapping("/series")
public class SerieController {

	@Autowired
	SerieService serieService;

	@GetMapping
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public List<Serie> listar() {
		return serieService.listar();
	}

	@PutMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Serie salvar(@RequestBody Serie serie) {
		return serieService.salvar(serie);
	}

	@PostMapping("/{serieId}")
	public ResponseEntity<Serie> atualizar(@PathVariable Long serieId, @RequestBody Serie serie) {
		Serie serieAtual = serieService.atualizar(serieId, serie);
		return ResponseEntity.status(HttpStatus.CREATED).body(serieAtual);
	}

	@DeleteMapping("/{serieId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long serieId) {
		serieService.deletar(serieId);
	}
}
