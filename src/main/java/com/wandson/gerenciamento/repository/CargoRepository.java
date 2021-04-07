package com.wandson.gerenciamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wandson.gerenciamento.model.Cargo;

public interface CargoRepository extends JpaRepository<Cargo, Long> {

}
