package com.wandson.gerenciamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wandson.gerenciamento.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
