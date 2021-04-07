package com.wandson.gerenciamento.resource;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.wandson.gerenciamento.dto.PerfilDTO;
import com.wandson.gerenciamento.event.RecursoCriadoEvent;
import com.wandson.gerenciamento.model.Perfil;
import com.wandson.gerenciamento.service.PerfilService;

@RestController
@RequestMapping("/perfis")
public class PerfilResource {

	@Autowired
	private PerfilService perfilService;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public Page<Perfil> listar(Pageable pageable) {
		return perfilService.listar(pageable);
	}

	@PostMapping
	public ResponseEntity<Perfil> criar(@Valid @RequestBody PerfilDTO perfilDTO, HttpServletResponse response) {
		Perfil perfil = modelMapper.map(perfilDTO, Perfil.class);
		perfil = perfilService.criar(perfil);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, perfil.getCodigo()));

		return ResponseEntity.status(HttpStatus.CREATED).body(perfil);
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Perfil> buscarPeloCodigo(@PathVariable Long codigo) {
		return perfilService.buscarPeloCodigo(codigo).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		perfilService.remover(codigo);
	}

	@PutMapping("/{codigo}")
	public ResponseEntity<Perfil> atualizar(@PathVariable Long codigo, @Valid @RequestBody PerfilDTO perfilDTO) {
		return ResponseEntity.ok(perfilService.atualizar(codigo, modelMapper.map(perfilDTO, Perfil.class)));
	}

}
