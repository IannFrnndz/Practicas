package com.viajessolparaiso.gestion_ofertas.controller;

import com.viajessolparaiso.gestion_ofertas.entity.Oferta;
import com.viajessolparaiso.gestion_ofertas.service.OfertaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ofertas")
public class OfertaController {

    private final OfertaService ofertaService;

    public OfertaController(OfertaService ofertaService) {
        this.ofertaService = ofertaService;
    }

    // =====================================
    // LISTA
    // =====================================
    @GetMapping({"", "/"})
    public String listar(Model model) {
        model.addAttribute("ofertas", ofertaService.findAll());
        return "ofertas/list";
    }

    // =====================================
    // DETALLE
    // =====================================
    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        Oferta oferta = ofertaService.findById(id);
        if (oferta == null) {
            throw new IllegalArgumentException("Oferta no encontrada");
        }

        model.addAttribute("oferta", oferta);
        return "ofertas/detail";
    }

    // =====================================
    // FORM NUEVA
    // =====================================
    @GetMapping("/nueva")
    public String nueva(Model model) {
        model.addAttribute("oferta", new Oferta());
        return "ofertas/form";
    }

    // =====================================
    // FORM EDITAR
    // =====================================
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Oferta oferta = ofertaService.findById(id);
        if (oferta == null) {
            throw new IllegalArgumentException("Oferta no encontrada");
        }

        model.addAttribute("oferta", oferta);
        return "ofertas/form";
    }

    // =====================================
    // GUARDAR (CREATE + UPDATE)
    // =====================================
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Oferta oferta) {
        ofertaService.save(oferta);
        return "redirect:/ofertas";
    }

    // =====================================
    // ELIMINAR
    // =====================================
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        ofertaService.delete(id);
        return "redirect:/ofertas";
    }
}