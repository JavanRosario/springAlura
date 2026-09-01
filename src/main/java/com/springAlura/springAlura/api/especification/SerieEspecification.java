package com.springAlura.springAlura.api.especification;

import org.springframework.data.jpa.domain.Specification;

import com.springAlura.springAlura.api.dto.SerieFiltroRequestDto;
import com.springAlura.springAlura.domain.model.Serie;

public class SerieEspecification {

	public static Specification<Serie> comFiltros(SerieFiltroRequestDto dto) {
		return Specification.where(porNome(dto.titulo()).and(porNota(dto.notaMax()))

		);
	}

	public static Specification<Serie> porNome(String nome) {
		return (root, query, cb) -> {
			if (nome == null || nome.trim().isEmpty()) {
				return null;
			}
			return cb.like(cb.lower(root.get("titulo")), "%" + nome.toLowerCase() + "%");
		};

	}

	public static Specification<Serie> porNota(Double nota) {
		return (root, query, cb) -> {
			if (nota == null || nota.isNaN()) {
				return null;
			}
			return cb.lessThanOrEqualTo(root.get("avaliacao"), nota);
		};
	}

	public static Specification<Serie> porAtores(String atores) {
		return (root, query, cb) -> {
			if (atores == null || atores.trim().isEmpty()) {
				return null;
			}

			return cb.like(cb.lower(root.get("atores")), "%" + atores.toLowerCase() + "%");
		};
	}

}
