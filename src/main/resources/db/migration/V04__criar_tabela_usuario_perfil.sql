CREATE TABLE usuario_perfil (
    codigo_usuario BIGINT NOT NULL,
    codigo_perfil BIGINT NOT NULL,
    CONSTRAINT pk_usuario_perfil PRIMARY KEY (codigo_usuario , codigo_perfil),
    CONSTRAINT fk_usuario FOREIGN KEY (codigo_usuario)
        REFERENCES usuario (codigo),
    CONSTRAINT fk_perfil FOREIGN KEY (codigo_perfil)
        REFERENCES perfil (codigo)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;