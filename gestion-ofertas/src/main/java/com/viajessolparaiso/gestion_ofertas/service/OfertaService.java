package com.viajessolparaiso.gestion_ofertas.service;

import com.viajessolparaiso.gestion_ofertas.entity.Categoria;
import com.viajessolparaiso.gestion_ofertas.entity.EstadoOferta;
import com.viajessolparaiso.gestion_ofertas.entity.Oferta;
import com.viajessolparaiso.gestion_ofertas.repository.OfertaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;
import java.time.LocalDate;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OfertaService {

    private final OfertaRepository ofertaRepository;

    // Obtener todas las ofertas
    public List<Oferta> findAll() {
        return ofertaRepository.findAll();
    }

    // Obtener solo publicadas para Flutter
    public List<Oferta> findPublicadasByCategoria(Categoria categoria) {
        return ofertaRepository.findByCategoriaAndEstado(
                categoria,
                EstadoOferta.PUBLICADA
        );
    }

    // pasar las ofertas publicadas a borrador cuando pase el tiempo limite
    @Scheduled(cron = "0 0 0 * * *")
    public void despublicarOfertasVencidas() {

        List<Oferta> ofertasVencidas =
                ofertaRepository.findByEstadoAndFechaValidezLessThan(
                        EstadoOferta.PUBLICADA,
                        LocalDate.now()
                );

        for (Oferta oferta : ofertasVencidas) {

            oferta.setEstado(EstadoOferta.BORRADOR);
            ofertaRepository.save(oferta);

        }
    }

    // Crear o actualizar oferta
    public Oferta save(Oferta oferta) {
        if (oferta.getFechaCreacion() == null) {
            oferta.setFechaCreacion(LocalDateTime.now());
        }
        if (oferta.getEstado() == null) {
            oferta.setEstado(EstadoOferta.BORRADOR);  // Estado por defecto
        }
        return ofertaRepository.save(oferta);
    }

    //  Buscar por ID
    public Oferta findById(Long id) {
        return ofertaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Oferta no encontrada"));
    }

    //  Eliminar oferta
    public void delete(Long id) {
        ofertaRepository.deleteById(id);
    }

    //  Publicar oferta
    public void publicar(Long id) {
        Oferta oferta = findById(id);
        oferta.setEstado(EstadoOferta.PUBLICADA);
        ofertaRepository.save(oferta);
    }

    //  Despublicar oferta
    public void despublicar(Long id) {
        Oferta oferta = findById(id);
        oferta.setEstado(EstadoOferta.BORRADOR);
        ofertaRepository.save(oferta);
    }
}