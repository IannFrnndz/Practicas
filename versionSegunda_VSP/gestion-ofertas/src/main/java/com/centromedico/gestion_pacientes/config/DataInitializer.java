package com.centromedico.gestion_pacientes.config;

import com.centromedico.gestion_pacientes.entity.Usuario;
import com.centromedico.gestion_pacientes.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Inicializador de datos
 * Se ejecuta al arrancar la aplicación y crea usuarios de prueba si no existen
 */
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("==========================================");
        System.out.println("🚀 INICIANDO CARGA DE DATOS DE PRUEBA");
        System.out.println("==========================================");

        // Verificar si ya existen usuarios
        long cantidadUsuarios = usuarioRepository.count();
        System.out.println("📊 Usuarios existentes en BD: " + cantidadUsuarios);

        if (cantidadUsuarios == 0) {
            System.out.println("⚠️ No hay usuarios en la BD. Creando usuarios de prueba...");
            crearUsuariosDePrueba();
        } else {
            System.out.println("✅ Ya existen usuarios en la BD.");

            // Mostrar los usuarios existentes
            usuarioRepository.findAll().forEach(usuario -> {
                System.out.println("   - " + usuario.getNombre() +" - Activo: " + usuario.getActivo());
            });

            // Opción: Actualizar contraseñas si es necesario
            System.out.println("\n🔄 ¿Quieres recrear las contraseñas? (comenta/descomenta esta línea)");
            // actualizarContrasenas(); // ⬅️ DESCOMENTA ESTA LÍNEA SI QUIERES RECREAR LAS CONTRASEÑAS
        }

        System.out.println("==========================================");
        System.out.println("✅ CARGA DE DATOS COMPLETADA");
        System.out.println("==========================================\n");
    }

    /**
     * Crea usuarios de prueba en la base de datos
     */
    private void crearUsuariosDePrueba() {
        String passwordPorDefecto = "password";
        String passwordEncriptado = passwordEncoder.encode(passwordPorDefecto);

        System.out.println("🔐 Contraseña por defecto para todos: " + passwordPorDefecto);
        System.out.println("🔐 Hash BCrypt generado: " + passwordEncriptado.substring(0, 30) + "...");

        // 1. ADMIN
        Usuario admin = new Usuario();
        admin.setNombre("admin1");
        admin.setEmail("admin@centromedico.com");
        admin.setPasswordHash(passwordEncriptado);
        admin.setNombre("Administrador Principal");
        admin.setActivo(true);
        usuarioRepository.save(admin);
        System.out.println("✅ Usuario creado: admin1 (ADMIN)");

    }

    /**
     * Actualiza las contraseñas de usuarios existentes
     * Útil si las contraseñas en BD están mal
     */
    private void actualizarContrasenas() {
        String passwordPorDefecto = "password";
        String passwordEncriptado = passwordEncoder.encode(passwordPorDefecto);

        System.out.println("\n🔄 ACTUALIZANDO CONTRASEÑAS DE USUARIOS EXISTENTES");
        System.out.println("🔐 Nueva contraseña: " + passwordPorDefecto);
        System.out.println("🔐 Nuevo hash: " + passwordEncriptado.substring(0, 30) + "...\n");

        usuarioRepository.findAll().forEach(usuario -> {
            String oldHash = usuario.getPasswordHash().substring(0, 30);
            usuario.setPasswordHash(passwordEncriptado);
            usuarioRepository.save(usuario);
            System.out.println("✅ Contraseña actualizada para: " + usuario.getNombre());
            System.out.println("   - Hash anterior: " + oldHash + "...");
            System.out.println("   - Hash nuevo:    " + passwordEncriptado.substring(0, 30) + "...");
        });

        System.out.println("\n✅ Todas las contraseñas han sido actualizadas");
    }


}