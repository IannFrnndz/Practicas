package com.viajessolparaiso.gestion_ofertas.controller;

import com.viajessolparaiso.gestion_ofertas.config.CustomUserDetails;
import com.viajessolparaiso.gestion_ofertas.entity.Oferta;
import com.viajessolparaiso.gestion_ofertas.service.GroqService;
import com.viajessolparaiso.gestion_ofertas.service.OfertaService;
import com.viajessolparaiso.gestion_ofertas.entity.Categoria;
import com.viajessolparaiso.gestion_ofertas.service.PdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;

@Controller
@RequestMapping("/ofertas")
@RequiredArgsConstructor
public class OfertaController {
    private final PdfService pdfService;

    private final OfertaService ofertaService;
    private final GroqService groqService;

    // LISTAR TODAS LAS OFERTAS
    @GetMapping({"", "/"})
    public String listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String buscar,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Model model
    ) {

        Page<Oferta> ofertasPage;

        if (buscar != null && !buscar.isBlank()) {

            ofertasPage = ofertaService.buscarPorTitulo(
                    buscar,
                    page,
                    size
            );

        } else {

            ofertasPage = ofertaService.findPaginated(
                    page,
                    size
            );
        }

        model.addAttribute("ofertas",
                ofertasPage.getContent());

        model.addAttribute("currentPage",
                page);

        model.addAttribute("totalPages",
                ofertasPage.getTotalPages());

        model.addAttribute("buscar",
                buscar);

        model.addAttribute("usuario",
                userDetails.getUsuario());

        return "ofertas/list";
    }

    // VER DETALLE DE UNA OFERTA
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

    // EXTRACCION DE TEXTO
    @PostMapping("/probar-pdf")
    public String probarPdf(
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Model model
    ) {

        try {

            String texto = pdfService.extraerTexto(file);

            var datosIa = groqService.analizarTexto(texto);

            Oferta oferta = new Oferta();

            oferta.setTitulo(datosIa.getTitulo());
            oferta.setDescripcion(datosIa.getDescripcion());
            oferta.setPrecio(datosIa.getPrecio());

            if (datosIa.getFechaValidez() != null &&
                    !datosIa.getFechaValidez().isBlank()) {

                oferta.setFechaValidez(
                        java.time.LocalDate.parse(datosIa.getFechaValidez())
                );
            }

            try {
                oferta.setCategoria(
                        Categoria.valueOf(
                                datosIa.getCategoria().trim().toUpperCase()
                        )
                );
            } catch (Exception e) {

                System.out.println("Categoria IA inválida: " + datosIa.getCategoria());

                oferta.setCategoria(Categoria.EUROPA);
            }
            oferta.setImagenUrl(datosIa.getImagenUrl());

            model.addAttribute("oferta", oferta);

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(e);
        }

        model.addAttribute("usuario", userDetails.getUsuario());
        model.addAttribute("categorias", Categoria.values());

        return "ofertas/form";
    }

    // FORMULARIO PARA CREAR NUEVA OFERTA
    @GetMapping("/nueva")
    public String nueva(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Model model
    ) {
        model.addAttribute("oferta", new Oferta());
        model.addAttribute("usuario", userDetails.getUsuario());
        model.addAttribute("categorias", Categoria.values());
        return "ofertas/form";
    }

    // FORMULARIO PARA EDITAR OFERTA
    @GetMapping("/editar/{id}")
    public String editar(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Model model
    ) {
        Oferta oferta = ofertaService.findById(id);
        model.addAttribute("oferta", oferta);
        model.addAttribute("usuario", userDetails.getUsuario());
        model.addAttribute("categorias", Categoria.values());
        return "ofertas/form";
    }

    // GUARDAR (CREATE + UPDATE)
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Oferta oferta) {
        ofertaService.save(oferta);
        return "redirect:/ofertas";
    }

    // ELIMINAR OFERTA
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        ofertaService.delete(id);
        return "redirect:/ofertas";
    }

    // PUBLICAR OFERTA
    @GetMapping("/publicar/{id}")
    public String publicar(@PathVariable Long id) {
        ofertaService.publicar(id);
        return "redirect:/ofertas";
    }

    // DESPUBLICAR OFERTA

    @GetMapping("/despublicar/{id}")
    public String despublicar(@PathVariable Long id) {
        ofertaService.despublicar(id);
        return "redirect:/ofertas";
    }
}
