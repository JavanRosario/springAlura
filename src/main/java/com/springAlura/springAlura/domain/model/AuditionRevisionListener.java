package com.springAlura.springAlura.domain.model;

import org.hibernate.envers.RevisionListener;
import org.springframework.stereotype.Component;

import com.springAlura.springAlura.configs.AuditAwareImpl;

@Component
public class AuditionRevisionListener implements RevisionListener {

	private final AuditAwareImpl auditAwareImpl = new AuditAwareImpl();

	@Override
	public void newRevision(Object revisionEntity) {
		AuditRevisionEntity auditEntity = (AuditRevisionEntity) revisionEntity;

		String email = auditAwareImpl.getCurrentAuditor().orElse("SEM_USUARIO");

		auditEntity.setUser(email);

	}
}
