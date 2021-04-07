package com.wandson.gerenciamento.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.wandson.gerenciamento.model.Cargo;
import com.wandson.gerenciamento.model.Perfil;
import com.wandson.gerenciamento.model.Pessoa;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO extends Pessoa {

	@NotNull
	private Cargo cargo;

	private List<Perfil> perfis;

}
