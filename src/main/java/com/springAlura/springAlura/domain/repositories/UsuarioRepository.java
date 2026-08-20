package com.springAlura.springAlura.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springAlura.springAlura.domain.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
