package com.springAlura.springAlura.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Categoria {
	ACAO("Action"), ROMANCE("Romance"), COMEDIA("Comedy"), DRAMA("Drama"), CRIME("Crime"), MISTERIO("Mystery"),
	FANTASIA("Fantasy"), FICCAO_CIENTIFICA("Sci-Fi"), TERROR("Horror"), ANIMACAO("Animation"), AVENTURA("Adventure"),
	DOCUMENTARIO("Documentary");

	Categoria(String categoria) {
		this.categoriaOmdb = categoria;
	}

	private String categoriaOmdb;

	@JsonCreator
	public static Categoria fromString(String text) {
		for (Categoria categoria : Categoria.values()) {
			if (categoria.categoriaOmdb.equalsIgnoreCase(text.trim())) {
				return categoria;
			}
		}
		throw new IllegalArgumentException("Nenhuma categoria encontrada");
	}
}
