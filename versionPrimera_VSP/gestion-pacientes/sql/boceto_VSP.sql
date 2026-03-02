DROP DATABASE IF EXISTS boceto_VSP;
CREATE DATABASE boceto_VSP ;
USE boceto_VSP;

-- =========================
-- ROLES
-- =========================
CREATE TABLE roles (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nombre ENUM('ADMIN', 'CLIENTE') NOT NULL UNIQUE
);

-- =========================
-- USUARIOS
-- =========================
CREATE TABLE usuarios (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,

  dni VARCHAR(9) UNIQUE,
  telefono VARCHAR(20),

  email VARCHAR(100) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,

  nombre VARCHAR(100) NOT NULL,

  fecha_nacimiento DATE NULL,
  activo BOOLEAN NOT NULL DEFAULT TRUE,

  fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- =========================
-- USUARIO_ROLES (Many-to-Many)
-- =========================
CREATE TABLE usuario_roles (
  usuario_id BIGINT NOT NULL,
  rol_id BIGINT NOT NULL,

  PRIMARY KEY (usuario_id, rol_id),

  CONSTRAINT fk_usuario_roles_usuario
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
    ON DELETE CASCADE,

  CONSTRAINT fk_usuario_roles_rol
    FOREIGN KEY (rol_id) REFERENCES roles(id)
    ON DELETE RESTRICT
);

