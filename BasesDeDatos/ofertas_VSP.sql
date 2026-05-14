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
VALUES 
('viajessolparaiso@gmail.com', '$2a$10$JjvQvaJe6KZef1S3jdPeIOJUkPJ06ZN8l2DuijGW.QTJ5ulD/nPCe', 'VSolparaiso');



CREATE TABLE ofertas (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  titulo VARCHAR(100) NOT NULL,
  descripcion TEXT,
  precio DECIMAL(10,2),
  fecha_validez DATE,
  imagen_url VARCHAR(500),
  categoria ENUM('DISNEY', 'FESTIVOS', 'ESPAÑA','EUROPA','AMERICA','CARIBE','AFRICA','ASIA','OCEANIA','EXCURSIONES','ULTIMA_HORA','COMUNIDAD_DE_MADRID','CRUCEROS'),
  estado ENUM('BORRADOR', 'PUBLICADA') DEFAULT 'BORRADOR',
  fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

select * from usuarios;
select * from ofertas;