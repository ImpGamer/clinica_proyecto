package com.imp.clinica.controllers;

import com.imp.clinica.entities.CitaAgendada;
import com.imp.clinica.services.CitaAgendadasService;
import com.imp.clinica.services.CitaService;
import com.imp.clinica.util.CitaAgendadaPDF;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        return "confirmarCita";
    }

    @GetMapping("/validado")
    public String agendarCita(Model model) {
        try {
            citaAgendadasService.crearCitaAgendada(this.citaGuardada);
        }catch (Exception e) {
            model.addAttribute("msg",e.getMessage());
            model.addAttribute("guardada", false);
            return "citaConfirmada";
        }
         model.addAttribute("msg","La cita se a agendado correctamente\nPuedes imprimir el PDF: ");
         model.addAttribute("guardada",true);
        return "citaConfirmada";
    }
    @GetMapping("/exportPDF")
    void generarPDFcitaAgendada(HttpServletResponse servlet) {
        servlet.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String fechaActual = dateFormatter.format(new Date());

        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=Tu_cita_"+fechaActual+".pdf";

        servlet.setHeader(cabecera,valor);
        CitaAgendadaPDF citaAgendadaPDF = new CitaAgendadaPDF(this.citaGuardada);
        try {
            citaAgendadaPDF.exportar(servlet);
        }catch (IOException e) {
            System.err.println(e.getMessage());
        }
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