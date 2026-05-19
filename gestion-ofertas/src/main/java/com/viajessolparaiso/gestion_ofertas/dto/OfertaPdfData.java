package com.viajessolparaiso.gestion_ofertas.dto;

import com.viajessolparaiso.gestion_ofertas.entity.Categoria;
import lombok.Data;

@Data
public class OfertaPdfData {

    private String titulo;

    private String descripcion;

    private Double precio;

    private String fechaValidez;

    private String categoria;

    private String imagenUrl;
}