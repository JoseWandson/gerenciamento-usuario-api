package com.wandson.gerenciamento.resource;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.wandson.gerenciamento.dto.UsuarioDTO;
import com.wandson.gerenciamento.event.RecursoCriadoEvent;
import com.wandson.gerenciamento.exceptionhandler.GerenciamentoUsuarioExceptionHandler.Erro;
import com.wandson.gerenciamento.model.Usuario;
import com.wandson.gerenciamento.service.UsuarioService;
import com.wandson.gerenciamento.service.exception.CargoInexistenteException;
import com.wandson.gerenciamento.service.exception.PerfilInexistenteException;

@RestController
@RequestMapping("/usuarios")
public class UsuarioResource {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private MessageSource messageSource;

	@GetMapping
	public Page<Usuario> listar(Pageable pageable) {
		return usuarioService.listar(pageable);
	}

	@PostMapping
	public ResponseEntity<Usuario> criar(@Valid @RequestBody UsuarioDTO usuarioDTO, HttpServletResponse response) {
		Usuario usuario = modelMapper.map(usuarioDTO, Usuario.class);
		usuario = usuarioService.criar(usuario);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, usuario.getCodigo()));

		return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Usuario> buscarPeloCodigo(@PathVariable Long codigo) {
		return usuarioService.buscarPeloCodigo(codigo).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		usuarioService.remover(codigo);
	}

	@PutMapping("/{codigo}")
	public ResponseEntity<Usuario> atualizar(@PathVariable Long codigo, @Valid @RequestBody UsuarioDTO usuarioDTO) {
		return ResponseEntity.ok(usuarioService.atualizar(codigo, modelMapper.map(usuarioDTO, Usuario.class)));
	}

	@ExceptionHandler({ CargoInexistenteException.class })
	public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(CargoInexistenteException ex) {
		String mensagemUsuario = messageSource.getMessage("cargo.inexistente", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}

	@ExceptionHandler({ PerfilInexistenteException.class })
	public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(PerfilInexistenteException ex) {
		String mensagemUsuario = messageSource.getMessage("perfil.inexistente", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}

}
