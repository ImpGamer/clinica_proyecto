package com.imp.clinica.controllers;

import com.imp.clinica.services.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/medicos")
public class MedicoController {
    @Autowired
    private MedicoService medicoService;
    @GetMapping
    String mostrarListaMedicos(Model model) {
        model.addAttribute("listaMedicos",medicoService.listaMedicos());
        return "listaMedicos";
    }
}
