package com.viajessolparaiso.gestion_ofertas.config;

import com.viajessolparaiso.gestion_ofertas.entity.Usuario;
import com.viajessolparaiso.gestion_ofertas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Servicio personalizado para cargar usuarios desde la base de datos
 * Spring Security lo usa para autenticar usuarios por EMAIL
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Buscar el usuario en la base de datos por EMAIL
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Usuario no encontrado con email: " + email
                ));

        // Verificar si el usuario está activo
        if (!usuario.getActivo()) {
            throw new UsernameNotFoundException(
                    "Usuario inactivo: " + email
            );
        }

        // Convertir el Usuario a UserDetails
        return new CustomUserDetails(usuario);
    }
}