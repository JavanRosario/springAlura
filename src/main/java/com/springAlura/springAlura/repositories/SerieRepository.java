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

}
