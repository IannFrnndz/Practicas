package com.viajessolparaiso.gestion_ofertas.repository;

import com.viajessolparaiso.gestion_ofertas.entity.Categoria;
import com.viajessolparaiso.gestion_ofertas.entity.EstadoOferta;
import com.viajessolparaiso.gestion_ofertas.entity.Oferta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface
OfertaRepository extends JpaRepository<Oferta, Long> {
    // por si se encuentra publicado o en borrador y saber cual es su categoria REST
    List<Oferta> findByCategoriaAndEstado(Categoria categoria, EstadoOferta estado);
    // para que las ofertas se despubliquen en cuanto llegue el dia (se mantiene el mismo dia y luego se va a borrador)
    List<Oferta> findByEstadoAndFechaValidezLessThan(EstadoOferta estado, LocalDate fecha);
    // buscador por el titulo
    Page<Oferta> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);


}

