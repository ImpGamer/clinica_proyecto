package com.imp.clinica.controllers;

import com.imp.clinica.entities.CitaAgendada;
import com.imp.clinica.services.CitaAgendadasService;
import com.imp.clinica.services.CitaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/agendarCita")
public class CitaAgendadaController {
    @Autowired
    private CitaAgendadasService citaAgendadasService;
    @Autowired
    private CitaService citaService;

    @GetMapping
    String formularioGenerarCita(Model model) {
        model.addAttribute("cita",new CitaAgendada());
        model.addAttribute("direccion","/agendarCita");
        try {
            model.addAttribute("listaCitas", citaService.listaCitas(false));
        }catch (Exception e) {
            model.addAttribute("msgError",e.getMessage());
        }
        return "formularioCita";
    }
    @PostMapping
    public String obtenerDatosFormulario(RedirectAttributes redirectAttributes, Model model,CitaAgendada citaAgendada) throws Exception {
        try {
            redirectAttributes.addFlashAttribute("citaGuardada", citaAgendadasService.almacenarDatos(citaAgendada));
        } catch (Exception e) {
            model.addAttribute("cita", citaAgendada);
            model.addAttribute("listaCitas", citaService.listaCitas(false));
            model.addAttribute("msgCitaError", e.getMessage());
            return "formularioCita";
        }
        return "redirect:/agendarCita/validarCita";
    }

    @GetMapping("/validarCita")
    public String validarCitaAgendada(HttpSession session, Model model, @ModelAttribute("citaGuardada") CitaAgendada citaAgendada) {
        session.setAttribute("citaConfirmada", citaAgendada);
        model.addAttribute("direccion", "/agendarCita/validado");
        return "confirmarCita";
    }

    @PostMapping("/validado")
    public String agendarCita(HttpSession session,RedirectAttributes redirect) {
        CitaAgendada citaAgendada = (CitaAgendada) session.getAttribute("citaConfirmada");
        citaAgendadasService.crearCitaAgendada(citaAgendada);
        redirect.addFlashAttribute("msgExito","Tu cita a sido agendada correctamente");
        return "redirect:/inicio";
    }
}