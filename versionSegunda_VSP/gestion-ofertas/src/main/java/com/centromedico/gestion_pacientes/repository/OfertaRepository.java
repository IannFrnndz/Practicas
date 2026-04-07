package com.centromedico.gestion_pacientes.repository;

import com.centromedico.gestion_pacientes.entity.EstadoOferta;
import com.centromedico.gestion_pacientes.entity.Oferta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfertaRepository extends JpaRepository<Oferta, Long> {
    List<Oferta> findByEstado(EstadoOferta estado);
}
