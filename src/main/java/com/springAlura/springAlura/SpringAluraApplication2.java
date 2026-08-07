package com.springAlura.springAlura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.springAlura.springAlura.service.OmdbService;
import com.springAlura.springAlura.service.ConverterDadosService;
import com.springAlura.springAlura.service.MenuService;

@SpringBootApplication
public class SpringAluraApplication2 implements CommandLineRunner {

	@Autowired
	MenuService menuService;

	@Autowired
	OmdbService api;

	@Autowired
	ConverterDadosService dados;

	public static void main(String[] args) {
		SpringApplication.run(SpringAluraApplication2.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		menuService.menu();
		System.out.println("CRIANDO TABELAS......");
	}
}
