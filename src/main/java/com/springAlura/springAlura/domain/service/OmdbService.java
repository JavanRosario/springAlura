package com.springAlura.springAlura.domain.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springAlura.springAlura.domain.exception.SerieNaoEncontradaException;
import com.springAlura.springAlura.domain.model.Episodio;
import com.springAlura.springAlura.domain.model.Serie;
import com.springAlura.springAlura.domain.model.Temporada;
import com.springAlura.springAlura.domain.repositories.EpisodioRepositoryy;
import com.springAlura.springAlura.domain.repositories.SerieRepository;

@Service
public class OmdbService {

	@Value("${urlOmdbApi}")
	private String urlOmdb;

	@Autowired
	ObjectMapper mapper;

	@Autowired
	SerieService serieService;

	@Autowired
	SerieRepository repository;

	@Autowired
	EpisodioRepositoryy episodioRepositoryy;

	private static final Logger log = LoggerFactory.getLogger(OmdbService.class);

	public Serie getSerie(String stringSerie, boolean salvar) {
		String json = getJsonSerie(stringSerie, false);
		Serie serie = converterDados(json, Serie.class);

		if (salvar) {
			serieService.salvar(serie);
		}

		return serie;
	}

	public List<Temporada> getTemporada(String serieUsuario) {
		Serie serieBuscada = getSerie(serieUsuario, false);

		List<Temporada> temporadas = new ArrayList<Temporada>();

		for (int i = 0; i < serieBuscada.getTotalTemporada(); i++) {
			String json = getSeason(serieUsuario, i + 1, false);
			Temporada temporada = converterDados(json, Temporada.class);
			temporadas.add(temporada);
		}

		return temporadas;
	}

	public void salvarEpisodios(String serieUsuario, Serie serie) {
		List<Temporada> temporadaSerie = getTemporada(serieUsuario);
		List<Episodio> episodios = temporadaSerie
				.stream().flatMap(s -> s.getEpisodios().stream().map(p -> new Episodio(p.id, p.getTitulo(),
						s.getTemporada(), p.getDataLancamento(), p.getNumeroEpisodio(), p.getAvaliacao(), serie)))
				.toList();

		episodioRepositoryy.saveAll(episodios);

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

			String json = client.get().uri(urlOmdb.formatted(serie)).retrieve().body(String.class);

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
