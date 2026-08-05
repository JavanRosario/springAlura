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
public class Menu {
	private static final Logger log = LoggerFactory.getLogger(BuscaApiService.class);

	Scanner sc = new Scanner(System.in);
	@Autowired
	BuscaApiService apiService;

	@Autowired
	ConverterDados converterDados;

	@Autowired
	SerieService serieService;

	@Autowired
	SerieRepository repository;

	@Autowired
	ObjectMapper mapper;

	public void menu() {
		int escolhaUsuario = 0;
		do {
			String escolhaTexto = null;
			String temporada;
			int escolhaId;

			String menuText = """
					=========================================
					   🎬  CINE-TRACKER - MANAGER v1.0  🎬
					=========================================
					[ 1 ]  Buscar Série e Temporada
					[ 2 ]  Buscar Série por Título
					[ 3 ]  Buscar Séries no Banco
					[ 4 ]  Sair
					=========================================
					Digite a opção desejada: """;

			System.out.println(menuText);
			escolhaUsuario = sc.nextInt();
			sc.nextLine();
			switch (escolhaUsuario) {
			case 1 -> {
				try {
					System.out.println(mapper.writeValueAsString(repository.findAll()));
				} catch (JsonProcessingException e) {

					e.printStackTrace();
				}
				System.out.println("Qual série? Escolha um id para listar a temporada");
				escolhaId = sc.nextInt();
				Optional<Serie> serie = repository.findById((long) escolhaId);
				String nomeSerieEncontradaPorId = serie.get().getTitle();
				apiService.getTemporada(nomeSerieEncontradaPorId);
//				System.out.println(serie.get());
				break;
			}
			case 2 -> {
				System.out.println("Qual Série deseja buscar?");
				escolhaTexto = sc.nextLine().replace(" ", "+");
				Serie serie = apiService.getSerie(escolhaTexto, true);
				System.out.println(serie);
				break;
			}

			case 3 -> {
				System.out.println(serieService.listar());
			}

			case 4 -> {
				System.out.println("Encerrando");
				break;
			}

			default -> throw new IllegalArgumentException("Unexpected value: " + escolhaUsuario);
			}

		} while (!(escolhaUsuario == 4));
		log.info("SAINDO DO PROGRAMA");
	}
}
