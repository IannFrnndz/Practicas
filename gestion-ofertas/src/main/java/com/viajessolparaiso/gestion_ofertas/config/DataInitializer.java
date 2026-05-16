package com.viajessolparaiso.gestion_ofertas.config;

import com.viajessolparaiso.gestion_ofertas.entity.Usuario;
import com.viajessolparaiso.gestion_ofertas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Inicializador de datos
 * Se ejecuta al arrancar la aplicación y crea el usuario admin si no existe
 */
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${ADMIN_EMAIL}")
    private String adminEmail;

    @Value("${ADMIN_PASSWORD}")
    private String adminPassword;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("==========================================");
        System.out.println("INICIANDO CARGA DE DATOS");
        System.out.println("==========================================");

        long cantidadUsuarios = usuarioRepository.count();

        System.out.println("Usuarios existentes en BD: " + cantidadUsuarios);

        if (cantidadUsuarios == 0) {

            System.out.println("No hay usuarios en la BD");
            System.out.println("Creando usuario administrador...");

            crearUsuarioAdmin();

        } else {

            System.out.println("Ya existen usuarios en la BD");

            usuarioRepository.findAll().forEach(usuario -> {
                System.out.println(
                        "   - " + usuario.getEmail()
                                + " | Activo: " + usuario.getActivo()
                );
            });
        }

        System.out.println("==========================================");
        System.out.println("CARGA DE DATOS COMPLETADA");
        System.out.println("==========================================");
    }

    /**
     * Crear usuario administrador inicial
     */
    private void crearUsuarioAdmin() {

        Usuario admin = new Usuario();

        admin.setNombre("Administrador");
        admin.setEmail(adminEmail);
        admin.setActivo(true);

        // La contraseña se guarda hasheada con BCrypt
        admin.setPasswordHash(
                passwordEncoder.encode(adminPassword)
        );

        usuarioRepository.save(admin);

        System.out.println("Usuario administrador creado correctamente");
        System.out.println("Email admin configurado desde variables de entorno");
    }
}