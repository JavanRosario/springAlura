package com.springAlura.springAlura.service;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springAlura.springAlura.entity.DadosSerie;

@Service
public class Menu {
	private static final Logger log = LoggerFactory.getLogger(BuscaApiService.class);

	Scanner sc = new Scanner(System.in);
	@Autowired
	BuscaApiService apiService;

	@Autowired
	ConverterDados converterDados;

	public void menu() {
		int escolhaUsuario = 0;
		do {
			String escolhaTexto;
			String temporada;

			String menuText = """
					=========================================
					   🎬  CINE-TRACKER - MANAGER v1.0  🎬
					=========================================
					[ 1 ]  Buscar Série e Temporada
					[ 2 ]  Buscar Série por Título
					[ 3 ]  Sair
					=========================================
					Digite a opção desejada: """;

			System.out.println(menuText);
			escolhaUsuario = sc.nextInt();
			sc.nextLine();
			switch (escolhaUsuario) {
			case 1 -> {
				System.out.println("Qual série?");
				escolhaTexto = sc.nextLine().replace(" ", "+");
				apiService.getTemporada(escolhaTexto);
				break;
			}
			case 2 -> {
				System.out.println("Qual Série deseja buscar?");
				escolhaTexto = sc.nextLine().replace(" ", "+");
				DadosSerie serie = apiService.getDadosSerie(escolhaTexto);
				System.out.println(serie);
				break;
			}
			case 3 -> {
				System.out.println("Encerrando");
				break;
			}

			default -> throw new IllegalArgumentException("Unexpected value: " + escolhaUsuario);
			}

		} while (!(escolhaUsuario == 3));
		log.info("SAINDO DO PROGRAMA");
	}
}
