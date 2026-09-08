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
@RevisionEntity(AuditionRevisionListener.class)
@AttributeOverrides({ @AttributeOverride(name = "timestamp", column = @Column(name = "rev_timestamp")),
		@AttributeOverride(name = "id", column = @Column(name = "revision_id")) })
public class AuditRevisionEntity extends DefaultRevisionEntity {

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "username")
	private String user;
}
