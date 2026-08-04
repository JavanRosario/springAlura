package com.springAlura.springAlura.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springAlura.springAlura.model.Serie;

public interface SerieRepository extends JpaRepository<Serie, Long> {

}
