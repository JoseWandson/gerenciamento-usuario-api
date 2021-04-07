package com.wandson.gerenciamento.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

public class CargoDTO {

	@Getter
	@Setter
	@NotBlank
	private String nome;

}
