package com.springAlura.springAlura.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Audited
@EntityListeners(AuditingEntityListener.class)
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private Integer totalTemporada;

    private Double avaliacao;

    private String atores;

    private String poster;

    @Column(columnDefinition = "TEXT")
    private String sinopse;

    @JsonFormat(pattern = "yyyy-mm-dd")
    private LocalDate dataLancamento;

    @Column(columnDefinition = "boolean default true")
    private Boolean ativo = true;

    @ManyToOne()
    @JoinColumn(name = "categoria_id")
    private Categoria categoria = null;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "series_streamings", joinColumns = @JoinColumn(name = "serie_id"), inverseJoinColumns = @JoinColumn(name = "streaming_id"))
    private List<Streaming> streaming;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;

//	@CreatedBy
//	@Column(updatable = false)
//	private String createdBy;
//
//	@LastModifiedBy
//	@Column(updatable = false)
//	private LocalDateTime lastModifieldBy;

    // https://sunitc.dev/2020/01/21/spring-boot-how-to-add-jpa-hibernate-envers-auditing/
}
