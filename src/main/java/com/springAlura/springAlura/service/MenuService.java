package com.springAlura.springAlura.service;

import java.util.Optional;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springAlura.springAlura.model.Serie;
import com.springAlura.springAlura.repositories.SerieRepository;

@Service
public class MenuService {
	private static final Logger log = LoggerFactory.getLogger(OmdbService.class);

	Scanner sc = new Scanner(System.in);
	@Autowired
	OmdbService apiService;

	@Autowired
	SerieService serieService;

	@Autowired
	SerieRepository repository;

	@Autowired
	ObjectMapper mapper;

	@Autowired
	EpisodioService episodioService;

	public void menu() {
		int escolhaUsuario = 0;
		do {
			String escolhaTexto = null;
			int escolhaId;
			Double nota;

			String menuText = """
					=========================================
					   🎬  CINE-TRACKER - MANAGER v1.0  🎬
					=========================================
					[ 1 ]  Buscar Série e Temporada
					[ 2 ]  Buscar Série por Título
					[ 3 ]  Buscar Séries no Banco
					[ 4 ]  Buscar Sérios por Ator
					[ 5 ]  Top 5 Séries
					[ 6 ]  Buscar Episodio por Trecho
					[ 0 ]  Sair
					=========================================
					Digite a opção desejada: """;

			System.out.println(menuText);
			escolhaUsuario = sc.nextInt();
			sc.nextLine();
			switch (escolhaUsuario) {
			case 1 -> {

				serieService.imprimirListaSerie(repository.findAll());

				System.out.println("Qual série? Escolha um id para listar a temporada");
				escolhaId = sc.nextInt();

				Optional<Serie> serie = repository.findById((long) escolhaId);
				String nomeSerieEncontradaPorId = serie.get().getTitle();

				apiService.salvarEpisodios(nomeSerieEncontradaPorId, serie.get());
				break;
			}
			case 2 -> {
				System.out.println("Qual Série deseja buscar?");
				escolhaTexto = sc.nextLine().replace(" ", "+");
				Serie serie = apiService.getSerie(escolhaTexto, true);
				serieService.imprimirSerie(serie);
				break;
			}

			case 3 -> {
				System.out.println("Qual serie deseja buscar?");
				escolhaTexto = sc.nextLine();
				try {
					System.out.println(
							mapper.writeValueAsString(repository.findByTitleContainingIgnoreCase(escolhaTexto)));
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			case 4 -> {
				System.out.println("Qual ator deseja buscar?");
				escolhaTexto = sc.nextLine();
				System.out.println(repository.findByActorsContainingIgnoreCase(escolhaTexto));
			}

			case 5 -> {
				serieService.imprimirListaSerie(repository.topCincoSeries());
			}

			case 6 -> {
				System.out.println("Qual episodio?");
				escolhaTexto = sc.nextLine();
				episodioService.imprimirEpisodio(repository.maiorData(escolhaTexto, 2011));
				System.out.println(repository.maiorData(escolhaTexto, 2019));

			}

			case 0 -> {
				System.out.println("Encerrando");
				break;
			}

			default -> throw new IllegalArgumentException("Unexpected value: " + escolhaUsuario);
			}

		} while (!(escolhaUsuario == 0));
		log.info("SAINDO DO PROGRAMA");
	}
}
