package com.springAlura.springAlura.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.springAlura.springAlura.domain.model.Serie;

public interface SerieRepository extends JpaRepository<Serie, Long>, JpaSpecificationExecutor<Serie> {

	List<Serie> findAllByOrderByIdAsc();

	List<Serie> findByTituloContainingIgnoreCase(String titulo);

	List<Serie> findByAtoresContainingIgnoreCase(String actor);

	List<Serie> findTop5ByOrderByAvaliacaoDesc();

	List<Serie> findTop5ByOrderByDataLancamentoDesc();

	List<Serie> findByAvaliacaoGreaterThanEqual(Double avaliacao);

	List<Serie> findTop3ByOrderByAvaliacaoAsc();

	@Query("from Serie order by avaliacao desc")
	List<Serie> topCincoSeries();

}
