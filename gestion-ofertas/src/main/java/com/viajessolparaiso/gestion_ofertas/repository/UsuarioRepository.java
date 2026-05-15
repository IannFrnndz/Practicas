package com.viajessolparaiso.gestion_ofertas.repository;


import com.viajessolparaiso.gestion_ofertas.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


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