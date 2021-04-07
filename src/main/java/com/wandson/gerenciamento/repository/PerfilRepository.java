package com.wandson.gerenciamento.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wandson.gerenciamento.model.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {

	boolean existsAllByCodigoIn(List<Long> codigos);

}
