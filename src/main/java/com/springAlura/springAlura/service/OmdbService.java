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
import com.springAlura.springAlura.exception.SerieNaoEncontradaException;
import com.springAlura.springAlura.model.Episodio;
import com.springAlura.springAlura.model.Serie;
import com.springAlura.springAlura.model.Temporada;
import com.springAlura.springAlura.repositories.EpisodioRepository;
import com.springAlura.springAlura.repositories.SerieRepository;

@Service
public class OmdbService {

	@Autowired
	ObjectMapper mapper;

	@Autowired
	SerieService serieService;

	@Autowired
	SerieRepository repository;

	@Autowired
	EpisodioRepository episodioRepository;

	private static final Logger log = LoggerFactory.getLogger(OmdbService.class);

	public Serie getSerie(String stringSerie, boolean salvar) {
		String json = getJsonSerie(stringSerie, false);
		Serie serie = converterDados(json, Serie.class);

		if (salvar) {
			serieService.salvar(serie);
		}

		return serie;
	}

	private List<Temporada> getTemporada(String serieUsuario) {
		Serie serieBuscada = getSerie(serieUsuario, false);

		List<Temporada> temporadas = new ArrayList<Temporada>();
		
		for (int i = 0; i < serieBuscada.getTotalSeasons(); i++) {
			String json = getSeason(serieUsuario, i + 1, false);
			Temporada temporada = converterDados(json, Temporada.class);
			temporadas.add(temporada);
		}
		

		return temporadas;
	}

	public void salvarEpisodios(String serieUsuario, Serie serie) {
		List<Temporada> temporadaSerie = getTemporada(serieUsuario);

		List<Episodio> episodios = temporadaSerie.stream().flatMap(s -> s.getEpisodes().stream()
				.map(p -> new Episodio(p.id, p.getTitle(), p.getReleased(), p.getEpisode(), p.getImdbRating(), serie)))
				.toList();

		episodioRepository.saveAll(episodios);

	}

	private <T> T converterDados(String json, Class<T> objeto) {
		try {
			return mapper.readValue(json, objeto);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}

	private String getJsonSerie(String serie, boolean print) {
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
