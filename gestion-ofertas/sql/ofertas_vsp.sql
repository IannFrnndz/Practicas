DROP DATABASE IF EXISTS ofertas_vsp;
CREATE DATABASE ofertas_vsp;
USE ofertas_vsp;

-- en este caso solo necesitamos un administrador
CREATE TABLE usuarios (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,

  email VARCHAR(100) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,

  nombre VARCHAR(100) NOT NULL,

  activo BOOLEAN NOT NULL DEFAULT TRUE,
  fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO usuarios (email, password_hash, nombre)
VALUES ('admin@admin.com', '1234_encriptado', 'Administrador');


CREATE TABLE ofertas (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,

  titulo VARCHAR(100) NOT NULL,
  descripcion TEXT,
  precio DECIMAL(10,2),

  fecha_validez DATE,

  imagen_url VARCHAR(500),

  estado ENUM('BORRADOR', 'PUBLICADA') DEFAULT 'BORRADOR',

  fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

select * from usuarios;