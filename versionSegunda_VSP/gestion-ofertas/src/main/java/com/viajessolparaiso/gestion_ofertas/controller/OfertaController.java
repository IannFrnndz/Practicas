package com.viajessolparaiso.gestion_ofertas.controller;

import com.viajessolparaiso.gestion_ofertas.config.CustomUserDetails;
import com.viajessolparaiso.gestion_ofertas.entity.Oferta;
import com.viajessolparaiso.gestion_ofertas.service.OfertaService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ofertas")
@RequiredArgsConstructor
public class OfertaController {

    private final OfertaService ofertaService;

    // =====================================
    // LISTAR TODAS LAS OFERTAS
    // =====================================
    @GetMapping({"", "/"})
    public String listar(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Model model
    ) {
        model.addAttribute("ofertas", ofertaService.findAll());
        model.addAttribute("usuario", userDetails.getUsuario());
        return "ofertas/list";
    }

    // =====================================
    // VER DETALLE DE UNA OFERTA
    // =====================================
    @GetMapping("/{id}")
    public String detalle(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Model model
    ) {
        Oferta oferta = ofertaService.findById(id);
        model.addAttribute("oferta", oferta);
        model.addAttribute("usuario", userDetails.getUsuario());
        return "ofertas/detail";
    }

    // =====================================
    // FORMULARIO PARA CREAR NUEVA OFERTA
    // =====================================
    @GetMapping("/nueva")
    public String nueva(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Model model
    ) {
        model.addAttribute("oferta", new Oferta());
        model.addAttribute("usuario", userDetails.getUsuario());
        return "ofertas/form";
    }

    // =====================================
    // FORMULARIO PARA EDITAR OFERTA
    // =====================================
    @GetMapping("/editar/{id}")
    public String editar(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Model model
    ) {
        Oferta oferta = ofertaService.findById(id);
        model.addAttribute("oferta", oferta);
        model.addAttribute("usuario", userDetails.getUsuario());
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
    // ELIMINAR OFERTA
    // =====================================
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        ofertaService.delete(id);
        return "redirect:/ofertas";
    }

    // =====================================
    // PUBLICAR OFERTA
    // =====================================
    @GetMapping("/publicar/{id}")
    public String publicar(@PathVariable Long id) {
        ofertaService.publicar(id);
        return "redirect:/ofertas";
    }

    // =====================================
    // DESPUBLICAR OFERTA
    // =====================================
    @GetMapping("/despublicar/{id}")
    public String despublicar(@PathVariable Long id) {
        ofertaService.despublicar(id);
        return "redirect:/ofertas";
    }
}
