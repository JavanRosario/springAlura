package com.springAlura.springAlura.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springAlura.springAlura.domain.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
