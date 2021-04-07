package com.wandson.gerenciamento.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties("gerenciamento-usuario")
public class GerenciamentoUsuarioApiProperty {

	@Getter
	@Setter
	private String originPermitida = "http://localhost:4200";

}
