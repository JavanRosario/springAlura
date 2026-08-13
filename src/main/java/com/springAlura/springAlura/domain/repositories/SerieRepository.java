package com.springAlura.springAlura.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springAlura.springAlura.domain.model.Episodio;
import com.springAlura.springAlura.domain.model.Serie;

public interface SerieRepository extends JpaRepository<Serie, Long> {

	List<Serie> findAllByOrderByIdAsc();

	List<Serie> findByTituloContainingIgnoreCase(String titulo);

	List<Serie> findByAtoresContainingIgnoreCase(String actor);

	List<Serie> findTop5ByOrderByAvaliacaoDesc();

	List<Serie> findTop5ByOrderByDataLancamentoDesc();

	List<Serie> findByAvaliacaoGreaterThanEqual(Double avaliacao);

	List<Serie> findTop3ByOrderByAvaliacaoAsc();

	@Query("from Serie where lower(atores) like lower (concat('%', :nomeAtor, '%')) and avaliacao >= :nota")
	List<Serie> findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String nomeAtor, Double nota);

	@Query("from Serie order by avaliacao desc")
	List<Serie> topCincoSeries();

	@Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE CONCAT('%', :nomeEpisodio, '%')")
	List<Episodio> trechosEpisodios(String nomeEpisodio);

	@Query("select s from Serie s join s.episodios e where lower(s.titulo) like lower (concat ('%', :nome, '%'))")
	List<Serie> melhorEpisodio(String nome);

//	@Query("select e from Serie s join s.episodios e where lower(s.titulo) like lower (concat ('%', :nome, '%')) order by e.avaliacao asc limit 5")
//	List<Episodio> top5EPisodiosDaSerie(String nome);
//
//	@Query("select e from Serie s join s.episodios e where lower(s.titulo) like lower (concat ('%', :nome, '%')) and Year(e.released) >= :data order by e.released asc")
//	List<Episodio> maiorData(String nome, int data);

}
