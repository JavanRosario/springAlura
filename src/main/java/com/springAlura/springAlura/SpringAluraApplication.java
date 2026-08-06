package com.springAlura.springAlura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.springAlura.springAlura.service.BuscaApiService;
import com.springAlura.springAlura.service.ConverterDadosService;
import com.springAlura.springAlura.service.Menu;

@SpringBootApplication
public class SpringAluraApplication implements CommandLineRunner {

	@Autowired
	Menu menu;

	@Autowired
	BuscaApiService api;

	@Autowired
	ConverterDadosService dados;

	public static void main(String[] args) {
		SpringApplication.run(SpringAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		menu.menu();
		System.out.println("CRIANDO TABELAS......");
	}
}
