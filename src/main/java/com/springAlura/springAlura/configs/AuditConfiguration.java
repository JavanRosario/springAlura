package com.springAlura.springAlura.configs;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.persistence.EntityManager;

@Configuration
public class AuditConfiguration {

	@Bean
	public AuditReader auditReader(EntityManager entityManager) {
		return AuditReaderFactory.get(entityManager);
	}
}
