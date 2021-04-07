package com.wandson.gerenciamento.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.wandson.gerenciamento.model.Perfil;
import com.wandson.gerenciamento.model.Usuario;
import com.wandson.gerenciamento.repository.CargoRepository;
import com.wandson.gerenciamento.repository.PerfilRepository;
import com.wandson.gerenciamento.repository.UsuarioRepository;
import com.wandson.gerenciamento.service.exception.CargoInexistenteException;
import com.wandson.gerenciamento.service.exception.PerfilInexistenteException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private CargoRepository cargoRepository;

	@Autowired
	private PerfilRepository perfilRepository;

	public Page<Usuario> listar(Pageable pageable) {
		return usuarioRepository.findAll(pageable);
	}

	public Usuario criar(Usuario usuario) {
		validarCargo(usuario);
		validarPerfil(usuario);

		return usuarioRepository.save(usuario);
	}

	public Optional<Usuario> buscarPeloCodigo(Long codigo) {
		return usuarioRepository.findById(codigo);
	}

	public void remover(Long codigo) {
		usuarioRepository.deleteById(codigo);
	}

	public Usuario atualizar(Long codigo, Usuario usuario) {
		Usuario usuarioSalvo = buscarPeloCodigo(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		if (!usuario.getCargo().equals(usuarioSalvo.getCargo())) {
			validarCargo(usuario);
		}
		validarPerfil(usuario);

		BeanUtils.copyProperties(usuario, usuarioSalvo, "codigo", "dataCadastro");
		return usuarioRepository.save(usuarioSalvo);
	}

	private void validarCargo(Usuario usuario) {
		if (!cargoRepository.existsById(usuario.getCargo().getCodigo())) {
			throw new CargoInexistenteException();
		}
	}

	private void validarPerfil(Usuario usuario) {
		if (CollectionUtils.isEmpty(usuario.getPerfis())) {
			return;
		}

		List<Long> perfis = usuario.getPerfis().stream().map(Perfil::getCodigo).collect(Collectors.toList());
		if (!perfilRepository.existsAllByCodigoIn(perfis)) {
			throw new PerfilInexistenteException();
		}
	}

}
