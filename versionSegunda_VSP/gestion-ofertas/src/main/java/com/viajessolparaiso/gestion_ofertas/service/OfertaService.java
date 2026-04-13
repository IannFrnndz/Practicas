package com.viajessolparaiso.gestion_ofertas.service;

import com.viajessolparaiso.gestion_ofertas.entity.EstadoOferta;
import com.viajessolparaiso.gestion_ofertas.entity.Oferta;
import com.viajessolparaiso.gestion_ofertas.repository.OfertaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OfertaService {

    private final OfertaRepository ofertaRepository;

    // 🔹 Obtener todas las ofertas
    public List<Oferta> findAll() {
        return ofertaRepository.findAll();
    }

    // 🔹 Obtener solo publicadas (para Flutter)
    public List<Oferta> findPublicadas() {
        return ofertaRepository.findByEstado(EstadoOferta.PUBLICADA);
    }

    // 🔹 Crear o actualizar oferta
    public Oferta save(Oferta oferta) {
        if (oferta.getFechaCreacion() == null) {
            oferta.setFechaCreacion(LocalDateTime.now());
        }
        return ofertaRepository.save(oferta);
    }

    // 🔹 Buscar por ID
    public Oferta findById(Long id) {
        return ofertaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Oferta no encontrada"));
    }

    // 🔹 Eliminar oferta
    public void delete(Long id) {
        ofertaRepository.deleteById(id);
    }

    // 🔹 Publicar oferta
    public void publicar(Long id) {
        Oferta oferta = findById(id);
        oferta.setEstado(EstadoOferta.PUBLICADA);
        ofertaRepository.save(oferta);
    }

    // 🔹 Despublicar oferta
    public void despublicar(Long id) {
        Oferta oferta = findById(id);
        oferta.setEstado(EstadoOferta.BORRADOR);
        ofertaRepository.save(oferta);
    }
}