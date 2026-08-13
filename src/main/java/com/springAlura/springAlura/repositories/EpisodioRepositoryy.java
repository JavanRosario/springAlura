package com.springAlura.springAlura.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springAlura.springAlura.model.Episodio;

@Repository
public interface EpisodioRepository extends JpaRepository<Episodio, Long> {

	
}
