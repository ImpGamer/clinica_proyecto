package com.imp.clinica.controllers;

import com.imp.clinica.entities.CitaAgendada;
import com.imp.clinica.services.CitaAgendadasService;
import com.imp.clinica.services.CitaService;
import jakarta.servlet.http.HttpServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/agendarCita")
public class CitaAgendadaController {
    @Autowired
    private CitaAgendadasService citaAgendadasService;
    @Autowired
    private CitaService citaService;
    private CitaAgendada citaGuardada;
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
    public String obtenerDatosFormulario(CitaAgendada citaAgendada,Model model) throws Exception {
        try {
            citaAgendadasService.almacenarDatos(citaAgendada);
        } catch (Exception e) {
            model.addAttribute("cita", citaAgendada);
            model.addAttribute("listaCitas", citaService.listaCitas(false));
            model.addAttribute("msgCitaError", e.getMessage());
            return "formularioCita";
        }
        this.citaGuardada = citaAgendada;
        return "redirect:/agendarCita/validarCita";
    }

    @GetMapping("/validarCita")
    public String validarCitaAgendada(Model model) {
        model.addAttribute("citaGuardada",this.citaGuardada);
        model.addAttribute("direccion","/agendarCita/validado");
        return "confirmarCita";
    }

    @PostMapping("/validado")
    public String agendarCita(RedirectAttributes redirect) {
        citaAgendadasService.crearCitaAgendada(this.citaGuardada);
        redirect.addFlashAttribute("msgExito","Tu cita a sido agendada correctamente");
        return "redirect:/inicio";
    }
    @GetMapping("/citaAgendada/exports/pdf")
    void generarPDFcitaAgendada(HttpServlet servlet) {
        
    }
    @GetMapping("/detalles/{id}")
    String mostrarDetallesdeCitaAgendada(@PathVariable Long id,Model model) {
        model.addAttribute("citaAgendada",citaAgendadasService.buscarCitaAgendada_ID(id));
        return "detallesCitaAgendada";
    }
    @GetMapping("/{id}")
    String eliminarCitaAgendada(@PathVariable Long id,RedirectAttributes redirect) {
       citaAgendadasService.eliminarCitaAgendada(id);
       redirect.addFlashAttribute("msgExito","La cita se ha eliminado correctamente");
       return "redirect:/administrar/citasAgendadas";
    }
}