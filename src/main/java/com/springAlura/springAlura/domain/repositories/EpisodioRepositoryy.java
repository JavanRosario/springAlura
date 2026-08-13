package com.springAlura.springAlura.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springAlura.springAlura.domain.model.Episodio;

@Repository
public interface EpisodioRepositoryy extends JpaRepository<Episodio, Long> {

	@Query("from Episodio s where s.serie.id = :serieId and s.temporada = :id")
	List<Episodio> temporadaUnica(Long serieId, Long id);

}
