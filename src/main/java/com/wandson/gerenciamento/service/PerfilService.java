package com.wandson.gerenciamento.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wandson.gerenciamento.model.Perfil;
import com.wandson.gerenciamento.repository.PerfilRepository;

@Service
public class PerfilService {

	@Autowired
	private PerfilRepository perfilRepository;

	public Page<Perfil> listar(Pageable pageable) {
		return perfilRepository.findAll(pageable);
	}

	public Perfil criar(Perfil perfil) {
		return perfilRepository.save(perfil);
	}

	public Optional<Perfil> buscarPeloCodigo(Long codigo) {
		return perfilRepository.findById(codigo);
	}

	public void remover(Long codigo) {
		perfilRepository.deleteById(codigo);
	}

	public Perfil atualizar(Long codigo, Perfil perfil) {
		Perfil perfilSalvo = buscarPeloCodigo(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		BeanUtils.copyProperties(perfil, perfilSalvo, "codigo");
		return perfilRepository.save(perfilSalvo);
	}

}
