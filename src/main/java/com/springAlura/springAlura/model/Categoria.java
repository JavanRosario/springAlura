package com.springAlura.springAlura.model;

public enum Categoria {
	ACAO("Action"), ROMANCE("Romance"), COMEDIA("Comedy"), DRAMA("Drama"), CRIME("Crime");

	Categoria(String categoria) {
		this.categoriaOmdb = categoria;
	}

	private String categoriaOmdb;

	public static Categoria fromString(String text) {
		for (Categoria categoria : Categoria.values()) {
			if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
				return categoria;
			}
		}
		throw new IllegalArgumentException("Nenhuma categoria encontrada");
	}
}
