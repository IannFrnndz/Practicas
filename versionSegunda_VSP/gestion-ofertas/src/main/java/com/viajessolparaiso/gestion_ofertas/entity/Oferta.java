package com.viajessolparaiso.gestion_ofertas.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class Oferta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Column(length = 2000)
    private String descripcion;

    private Double precio;

    private LocalDate fechaValidez;

    private String imagenUrl;

    @Enumerated(EnumType.STRING)
    private EstadoOferta estado;

    private LocalDateTime fechaCreacion;
}