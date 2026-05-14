package com.viajessolparaiso.gestion_ofertas.controller;

import com.viajessolparaiso.gestion_ofertas.entity.Categoria;
import com.viajessolparaiso.gestion_ofertas.entity.Oferta;
import com.viajessolparaiso.gestion_ofertas.service.OfertaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ofertas")
@RequiredArgsConstructor
public class OfertaRestController {

    private final OfertaService ofertaService;

    // LISTAR TODAS LAS OFERTAS PUBLICADAS POR LA CATEGORIA CORRECTA
    @GetMapping("/categoria/{categoria}")
    public List<Oferta> porCategoria(@PathVariable Categoria categoria) {
        return ofertaService.findPublicadasByCategoria(categoria);
    }

    // VER DETALLE DE UNA OFERTA
    @GetMapping("/{id}")
    public Oferta detalle(@PathVariable Long id) {
        return ofertaService.findById(id);
    }

    // CREAR OFERTA
    @PostMapping
    public Oferta crear(@RequestBody Oferta oferta) {
        return ofertaService.save(oferta);
    }

    // EDITAR OFERTA
    @PutMapping("/{id}")
    public Oferta editar(@PathVariable Long id, @RequestBody Oferta oferta) {
        oferta.setId(id);
        return ofertaService.save(oferta);
    }

    // ELIMINAR OFERTA
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        ofertaService.delete(id);
    }

    // PUBLICAR OFERTA
    @PutMapping("/{id}/publicar")
    public void publicar(@PathVariable Long id) {
        ofertaService.publicar(id);
    }

    // DESPUBLICAR OFERTA
    @PutMapping("/{id}/despublicar")
    public void despublicar(@PathVariable Long id) {
        ofertaService.despublicar(id);
    }
}