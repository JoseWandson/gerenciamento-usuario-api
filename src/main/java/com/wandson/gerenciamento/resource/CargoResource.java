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

import com.wandson.gerenciamento.dto.CargoDTO;
import com.wandson.gerenciamento.event.RecursoCriadoEvent;
import com.wandson.gerenciamento.model.Cargo;
import com.wandson.gerenciamento.service.CargoService;

@RestController
@RequestMapping("/cargos")
public class CargoResource {

	@Autowired
	private CargoService cargoService;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public Page<Cargo> listar(Pageable pageable) {
		return cargoService.listar(pageable);
	}

	@PostMapping
	public ResponseEntity<Cargo> criar(@Valid @RequestBody CargoDTO cargoDTO, HttpServletResponse response) {
		Cargo cargo = modelMapper.map(cargoDTO, Cargo.class);
		cargo = cargoService.criar(cargo);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, cargo.getCodigo()));

		return ResponseEntity.status(HttpStatus.CREATED).body(cargo);
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Cargo> buscarPeloCodigo(@PathVariable Long codigo) {
		return cargoService.buscarPeloCodigo(codigo).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		cargoService.remover(codigo);
	}

	@PutMapping("/{codigo}")
	public ResponseEntity<Cargo> atualizar(@PathVariable Long codigo, @Valid @RequestBody CargoDTO cargoDTO) {
		return ResponseEntity.ok(cargoService.atualizar(codigo, modelMapper.map(cargoDTO, Cargo.class)));
	}

}
