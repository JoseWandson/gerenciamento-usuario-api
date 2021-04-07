CREATE TABLE usuario (
    codigo BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    cpf VARCHAR(14) NOT NULL,
    data_nascimento DATE,
    sexo CHAR,
    codigo_cargo BIGINT NOT NULL,
    data_cadastro DATETIME NOT NULL,
    PRIMARY KEY (codigo),
    FOREIGN KEY (codigo_cargo) REFERENCES cargo(codigo),
    UNIQUE (cpf)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;