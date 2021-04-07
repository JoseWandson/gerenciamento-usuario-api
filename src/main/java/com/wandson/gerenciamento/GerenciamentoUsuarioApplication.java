package com.wandson.gerenciamento;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.wandson.gerenciamento.config.property.GerenciamentoUsuarioApiProperty;

@SpringBootApplication
@EnableConfigurationProperties(GerenciamentoUsuarioApiProperty.class)
public class GerenciamentoUsuarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(GerenciamentoUsuarioApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
