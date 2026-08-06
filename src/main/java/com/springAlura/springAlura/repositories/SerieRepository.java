package com.springAlura.springAlura.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springAlura.springAlura.model.Serie;

public interface SerieRepository extends JpaRepository<Serie, Long> {

	List<Serie> findByTitleIgnoreCase(String title);

}
