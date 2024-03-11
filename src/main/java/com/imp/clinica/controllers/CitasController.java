package com.imp.clinica.controllers;

import com.imp.clinica.entities.Cita;
import com.imp.clinica.services.CitaService;
import com.imp.clinica.services.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/citas")
public class CitasController {
    @Autowired
    private CitaService citaService;
    @Autowired
    private MedicoService medicoService;

    @GetMapping("/consulta")
    String listadoCitas(Model model, @RequestParam(name = "disponibles",defaultValue = "false")String disponibles) {
        boolean mostrarTodas = Boolean.parseBoolean(disponibles);

        model.addAttribute("disponibles",disponibles);
        try {
            model.addAttribute("listaCitas",citaService.listaCitas(mostrarTodas));
        }catch (Exception e) {
            model.addAttribute("msgError",e.getMessage());
        }
        return "listaCitas";
    }
    @GetMapping("/nuevaCita")
    String formularioNuevaCita(Model model) {
        model.addAttribute("cita",new Cita());
        model.addAttribute("listaMedicos",medicoService.listaMedicos());
        model.addAttribute("direccion","/citas/nuevaCita");
        return "formularioCitaAgendada";
    }
    @PostMapping("/nuevaCita")
    String capturarNuevaCita(Model model, Cita cita,RedirectAttributes redirect) {
        try {
            citaService.agregarCita(cita);
        }catch (Exception e) {
            model.addAttribute("cita",cita);
            model.addAttribute("direccion","/citas/nuevaCita");
            model.addAttribute("listaMedicos",medicoService.listaMedicos());
            model.addAttribute("msgError",e.getMessage());
            return "formularioCitaAgendada";
        }
        redirect.addFlashAttribute("msgExito","La cita se a guardado correctamente");
        return "redirect:/administrar/citasAlmacenadas";
    }
    @GetMapping("/editar/{id}")
    String formularioEditarCita(@PathVariable Long id,Model model) {
        model.addAttribute("cita",citaService.buscarCita_ID(id));
        model.addAttribute("direccion","/citas/editar/"+id);
        model.addAttribute("listaMedicos",medicoService.listaMedicos());
        return "formularioCitaAgendada";
    }
    @PostMapping("/editar/{id}")
    String capturarCitaEditada(@PathVariable Long id,Model model,RedirectAttributes redirect,Cita cita) {
        try {
            citaService.editarCita(id,cita);
        }catch (Exception e) {
            model.addAttribute("msgError",e.getMessage());
            model.addAttribute("listaMedicos",medicoService.listaMedicos());
            model.addAttribute("cita",cita);
            return "formularioCitaAgendada";
        }
        redirect.addFlashAttribute("msgExito","La cita a sido actualizada correctamente");
        return "redirect:/administrar/citasAlmacenadas";
    }
    @GetMapping("/{id}")
    String eliminarCita(@PathVariable Long id,RedirectAttributes redirect) {
        citaService.eliminarCita(id);
        redirect.addFlashAttribute("msgExito","La cita a sido eliminada correctamente");
        return "redirect:/administrar/citasAlmacenadas";
    }
}