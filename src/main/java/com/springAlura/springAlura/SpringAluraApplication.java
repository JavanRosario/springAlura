package com.springAlura.springAlura;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringAluraApplication {
	

	public static void main(String[] args) {
		System.out.println("oi");
		SpringApplication.run(SpringAluraApplication.class, args);

	}
}
