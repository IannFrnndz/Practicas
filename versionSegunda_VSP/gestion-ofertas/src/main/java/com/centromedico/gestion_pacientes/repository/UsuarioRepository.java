package com.centromedico.gestion_pacientes.repository;


import com.centromedico.gestion_pacientes.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad Usuario
 * Proporciona métodos para acceder a los datos de usuarios
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);



    /**
     * Verifica si existe un usuario con ese email
     * @param email correo electrónico
     * @return true si existe, false si no
     */
    boolean existsByEmail(String email);
}