package com.springAlura.springAlura.domain.model;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "revision_info")
@RevisionEntity
@AttributeOverrides({ @AttributeOverride(name = "timestamp", column = @Column(name = "rev_timestamp")),
		@AttributeOverride(name = "id", column = @Column(name = "revision_id")) })
public class AuditRevisionEntity extends DefaultRevisionEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "username")
	private String user;
}
