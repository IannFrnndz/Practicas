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
@RequestMapping("/dashboard")

public class AdminController {

    private final OfertaService ofertaService;

    public AdminController(OfertaService ofertaService) {
        this.ofertaService = ofertaService;
    }

    @GetMapping("/dashboard")
    public String panel(Model model) {
        model.addAttribute("ofertas", ofertaService.findAll());
        return "dashboard";
    }

    @PostMapping("/crear")
    public String crear(Oferta oferta) {
        ofertaService.save(oferta);
        return "redirect:/dashboard";
    }

    @GetMapping("/publicar/{id}")
    public String publicar(@PathVariable Long id) {
        ofertaService.publicar(id);
        return "redirect:/dashboard";
    }

    @GetMapping("/borrar/{id}")
    public String borrar(@PathVariable Long id) {
        ofertaService.delete(id);
        return "redirect:/dashboard";
    }
}