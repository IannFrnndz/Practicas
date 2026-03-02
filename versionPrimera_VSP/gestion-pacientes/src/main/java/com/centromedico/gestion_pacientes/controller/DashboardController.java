package com.centromedico.gestion_pacientes.controller;

import com.centromedico.gestion_pacientes.config.CustomUserDetails;
import com.centromedico.gestion_pacientes.entity.Rol;
import com.centromedico.gestion_pacientes.entity.Usuario;
import com.centromedico.gestion_pacientes.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador del Dashboard principal
 * Redirige según el rol del usuario
 */
@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final UsuarioService usuarioService;

    /**
     * Página principal - Redirige al dashboard
     */
    @GetMapping("/")
    public String index() {
        return "redirect:/dashboard";
    }

    /**
     * Dashboard principal
     * Muestra información según el rol del usuario
     */
    @GetMapping("/dashboard")
    public String dashboard(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Model model
    ) {
        Usuario usuario = userDetails.getUsuario();
        model.addAttribute("usuario", usuario);




        return "dashboard";
    }

    /**
     * Página de inicio (alias de dashboard)
     */
    @GetMapping("/home")
    public String home() {
        return "redirect:/dashboard";
    }
}