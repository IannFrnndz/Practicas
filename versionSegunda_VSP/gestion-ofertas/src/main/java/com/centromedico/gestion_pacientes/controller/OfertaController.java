package com.centromedico.gestion_pacientes.controller;

import com.centromedico.gestion_pacientes.entity.EstadoOferta;
import com.centromedico.gestion_pacientes.entity.Oferta;
import com.centromedico.gestion_pacientes.service.OfertaService;
import com.centromedico.gestion_pacientes.repository.OfertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ofertas")
public class OfertaController {

    @Autowired
    private OfertaRepository ofertaRepository;
    private OfertaService ofertaService;

    // solo para ofertas publicadas
    @GetMapping
    public List<Oferta> getOfertas() {
        return ofertaRepository.findByEstado(EstadoOferta.PUBLICADA);
    }

}