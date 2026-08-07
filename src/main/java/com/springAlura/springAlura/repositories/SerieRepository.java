package com.springAlura.springAlura.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springAlura.springAlura.model.Episodio;
import com.springAlura.springAlura.model.Serie;

public interface SerieRepository extends JpaRepository<Serie, Long> {

	List<Serie> findByTitleContainingIgnoreCase(String title);

	List<Serie> findByActorsContainingIgnoreCase(String actor);

//	List<Serie> findTop5ByOrderByImdbRatingDesc();

	List<Serie> findByImdbRatingGreaterThanEqual(Double ImdbRating);

	List<Serie> findTop3ByOrderByImdbRatingAsc();

	@Query("from Serie where lower(actors) like lower (concat('%', :nomeAtor, '%')) and imdbRating >= :nota")
	List<Serie> findByActorsContainingIgnoreCaseAndImdbRatingGreaterThanEqual(String nomeAtor, Double nota);

	@Query("from Serie order by imdbRating desc")
	List<Serie> topCincoSeries();

	@Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.title ILIKE CONCAT('%', :nomeEpisodio, '%')")
	List<Episodio> trechosEpisodios(String nomeEpisodio);

	@Query("select s from Serie s join s.episodios e where lower(s.title) like lower (concat ('%', :nome, '%'))")
	List<Serie> melhorEpisodio(String nome);

	@Query("select e from Serie s join s.episodios e where lower(s.title) like lower (concat ('%', :nome, '%')) order by e.imdbRating asc limit 5")
	List<Episodio> top5EPisodiosDaSerie(String nome);

	@Query("select e from Serie s join s.episodios e where lower(s.title) like lower (concat ('%', :nome, '%')) and Year(e.released) >= :data order by e.released asc")
	List<Episodio> maiorData(String nome, int data);

}
