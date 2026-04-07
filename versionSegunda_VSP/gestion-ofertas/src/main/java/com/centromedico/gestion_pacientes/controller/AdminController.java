package com.centromedico.gestion_pacientes.controller;

import com.centromedico.gestion_pacientes.entity.Oferta;
import com.centromedico.gestion_pacientes.service.OfertaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/admin")

public class AdminController {

    private OfertaService ofertaService;

    @GetMapping
    public String panel(Model model) {
        model.addAttribute("ofertas", ofertaService.findAll());
        return "admin";
    }

    @PostMapping("/crear")
    public String crear(Oferta oferta) {
        ofertaService.save(oferta);
        return "redirect:/admin";
    }

    @GetMapping("/publicar/{id}")
    public String publicar(@PathVariable Long id) {
        ofertaService.publicar(id);
        return "redirect:/admin";
    }

    @GetMapping("/borrar/{id}")
    public String borrar(@PathVariable Long id) {
        ofertaService.delete(id);
        return "redirect:/admin";
    }
}