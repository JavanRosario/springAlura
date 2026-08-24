package com.springAlura.springAlura.domain.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Plataforma {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	private String dominio;

	private Long totalCatalogo;

	private Double mensalidade;

	@OneToMany(mappedBy = "plataforma")
	private List<Streaming> streaming = new ArrayList<>();
}
