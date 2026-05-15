package com.viajessolparaiso.gestion_ofertas.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http

                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/**")
                )
                // ============================================
                // RUTAS PÚBLICAS
                // ============================================
                .authorizeHttpRequests(auth -> auth

                        // API pública para Flutter
                        .requestMatchers(HttpMethod.GET, "/api/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")

                        // Login admin
                        .requestMatchers("/login", "/css/**", "/js/**", "/img/**").permitAll()

                        // Panel admin web - requiere autenticación
                        .requestMatchers("/panel/**").hasRole("ADMIN")
                        .requestMatchers("/dashboard/**").hasRole("ADMIN")

                        // todo lo demás protegido
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
                        .usernameParameter("email")      // Parámetro del formulario: email
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
                .userDetailsService(customUserDetailsService)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}