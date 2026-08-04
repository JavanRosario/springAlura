package com.springAlura.springAlura.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springAlura.springAlura.entity.DadosSerie;
import com.springAlura.springAlura.entity.DadosTemporada;
import com.springAlura.springAlura.exception.SerieNaoEncontradaException;

@Service
public class BuscaApiService {

	@Autowired
	ObjectMapper mapper;

	private static final Logger log = LoggerFactory.getLogger(BuscaApiService.class);

	public <T> T converterDados(String json, Class<T> objeto) {
		try {
			return mapper.readValue(json, objeto);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}

	public DadosSerie getDadosSerie(String serie) {
		String json = getSerie(serie, false);
		DadosSerie dadosSerie = converterDados(json, DadosSerie.class);
		return dadosSerie;
	}

	public void getTemporada(String serie) {
		DadosSerie dadosSerie = getDadosSerie(serie);
		List<DadosTemporada> temporadas = new ArrayList<DadosTemporada>();

		for (int i = 0; i < dadosSerie.totalSeasons(); i++) {
			String json = getSeason(serie, i + 1, false);
			DadosTemporada dadosTemporada = converterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}

		DadosTemporada.imprimirSeason2(temporadas);

	}

	public void imprimirTemporadas(List<DadosTemporada> temporadas) {
		for (int i = 0; i < temporadas.size(); i++) {
			System.out.println("Temporada: %d\nEpisódios:\n===================================\n%s"
					.formatted(temporadas.get(i).season(), temporadas.get(1).episodes().toString()));
		}
	}

	private String getSerie(String serie, boolean print) {
		String tokenBase64 = proxySettings();

		RestClient client = RestClient.builder().defaultHeader("Proxy-Authorization", "Basic " + tokenBase64)
				.defaultHeader("User-Agent", "Mozilla/5.0").build();

		try {

			String json = client.get().uri("http://www.omdbapi.com/?t=%s&apikey=ead87646".formatted(serie)).retrieve()
					.body(String.class);

			if (json != null && json.contains("False")) {
				throw new SerieNaoEncontradaException("Serie não encontrada... Tente novamente..");
			}

			String formatado = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readTree(json));

			if (print) {
				System.out.println(formatado);
			}

			return formatado;

		} catch (JsonProcessingException e) {
			e.printStackTrace();
			log.error("Erro no processamento do Jackson");
			return e.getMessage();
		}

	}

	private String getEpisode(String serie, int temporada, String episodeo, boolean print) {
		String tokenBase64 = proxySettings();
		RestClient client = RestClient.builder().defaultHeader("Proxy-Authorization", "Basic " + tokenBase64)
				.defaultHeader("User-Agent", "Mozilla/5.0").build();

		try {

			String json = client.get().uri("http://www.omdbapi.com/?t=%s&season=%s&episode=%s&apikey=ead87646"
					.formatted(serie, temporada, episodeo)).retrieve().body(String.class);

			if (json != null && json.contains("False")) {
				throw new SerieNaoEncontradaException("Serie não encontrada... Tente novamente..");
			}
			String formatado = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readTree(json));

			if (print) {
				System.out.println(formatado);
			}

			return formatado;

		} catch (JsonProcessingException e) {
			e.printStackTrace();
			log.error("Erro no processamento do Jackson");
			return e.getMessage();
		}

	}

	private String getSeason(String serie, int i, boolean print) {
		String tokenBase64 = proxySettings();
		RestClient client = RestClient.builder().defaultHeader("Proxy-Authorization", "Basic " + tokenBase64)
				.defaultHeader("User-Agent", "Mozilla/5.0").build();

		try {

			String json = client.get()
					.uri("http://www.omdbapi.com/?t=%s&season=%s&&apikey=ead87646".formatted(serie, String.valueOf(i)))
					.retrieve().body(String.class);

			if (json != null && json.contains("False")) {
				throw new SerieNaoEncontradaException("Serie não encontrada... Tente novamente..");
			}
			String formatado = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readTree(json));

			if (print) {
				System.out.println(formatado);
			}

			return formatado;

		} catch (JsonProcessingException e) {
			e.printStackTrace();
			log.error("Erro no processamento do Jackson");
			return e.getMessage();
		}

	}

	private String proxySettings() {
		System.setProperty("http.proxyHost", "proxy-1dn.mb");
		System.setProperty("http.proxyPort", "6060");
		System.setProperty("https.proxyHost", "proxy-1dn.mb");
		System.setProperty("https.proxyPort", "6060");

		String auth = "19032388703:J@van10203040";
		String tokenBase64 = Base64.getEncoder().encodeToString(auth.getBytes());

		return tokenBase64;
	}
}
