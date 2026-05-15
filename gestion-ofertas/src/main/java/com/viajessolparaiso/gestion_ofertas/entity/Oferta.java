package com.viajessolparaiso.gestion_ofertas.entity;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "ofertas")
public class Oferta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 100, message = "El título no puede superar 100 caracteres")
    private String titulo;

    @Column(length = 2000)
    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", message = "El precio no puede ser negativo")
    private Double precio;

    @NotNull(message = "La fecha de validez es obligatoria")
    private LocalDate fechaValidez;

    private String imagenUrl;

    @NotNull(message = "La categoría es obligatoria")
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @Enumerated(EnumType.STRING)
    private EstadoOferta estado;

    private LocalDateTime fechaCreacion;
}