package com.springAlura.springAlura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.springAlura.springAlura.service.OmdbService;
import com.springAlura.springAlura.service.ConverterDadosService;
import com.springAlura.springAlura.service.MenuService;

@SpringBootApplication
public class SpringAluraApplication implements CommandLineRunner {

	@Autowired
	MenuService menuService;

	@Autowired
	OmdbService api;

	@Autowired
	ConverterDadosService dados;

	public static void main(String[] args) {
		SpringApplication.run(SpringAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		menuService.menu();
		System.out.println("CRIANDO TABELAS......");
	}
}
