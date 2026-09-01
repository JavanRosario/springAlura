package com.springAlura.springAlura;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringAluraApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringAluraApplication.class, args);

	}
}
