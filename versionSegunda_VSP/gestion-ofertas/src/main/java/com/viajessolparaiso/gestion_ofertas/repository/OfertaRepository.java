package com.viajessolparaiso.gestion_ofertas.repository;

import com.viajessolparaiso.gestion_ofertas.entity.EstadoOferta;
import com.viajessolparaiso.gestion_ofertas.entity.Oferta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfertaRepository extends JpaRepository<Oferta, Long> {
    List<Oferta> findByEstado(EstadoOferta estado);
}
