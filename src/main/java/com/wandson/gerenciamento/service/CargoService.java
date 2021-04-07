package com.wandson.gerenciamento.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wandson.gerenciamento.model.Cargo;
import com.wandson.gerenciamento.repository.CargoRepository;

@Service
public class CargoService {

	@Autowired
	private CargoRepository cargoRepository;

	public Page<Cargo> listar(Pageable pageable) {
		return cargoRepository.findAll(pageable);
	}

	public Cargo criar(Cargo cargo) {
		return cargoRepository.save(cargo);
	}

	public Optional<Cargo> buscarPeloCodigo(Long codigo) {
		return cargoRepository.findById(codigo);
	}

	public void remover(Long codigo) {
		cargoRepository.deleteById(codigo);
	}

	public Cargo atualizar(Long codigo, Cargo cargo) {
		Cargo cargoSalvo = buscarPeloCodigo(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		BeanUtils.copyProperties(cargo, cargoSalvo, "codigo");
		return cargoRepository.save(cargoSalvo);
	}

}
