package com.centromedico.gestion_pacientes.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // En tu BD: nombre ENUM('ADMIN','CLIENTE') NOT NULL UNIQUE
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private Rol nombre;
}