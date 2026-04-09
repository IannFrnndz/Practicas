package com.centromedico.gestion_pacientes.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // ============================================
                // RUTAS PÚBLICAS
                // ============================================
                .authorizeHttpRequests(auth -> auth

                        // API pública para Flutter
                        .requestMatchers("/api/ofertas/**").permitAll()

                        // Login admin
                        .requestMatchers("/login", "/css/**", "/js/**", "/img/**").permitAll()

                        // Panel admin web
                        .requestMatchers("/dashboard/**").hasRole("ADMIN")

                        // todo lo demas protegido
                        .anyRequest().authenticated()
                )

                // ============================================
                // LOGIN FORM
                // ============================================
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .failureUrl("/login?error=true")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .permitAll()
                )

                // ============================================
                // LOGOUT
                // ============================================
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )

                // ============================================
                // ERRORES
                // ============================================
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/acceso-denegado")
                )

                // ============================================
                // USER DETAILS SERVICE
                // ============================================
                .userDetailsService(customUserDetailsService);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}