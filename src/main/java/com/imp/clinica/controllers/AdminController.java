package com.imp.clinica.controllers;

import com.imp.clinica.services.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administrar")
public class AdminController {
    @Autowired
    private CitaService citaService;
    @GetMapping("/citasAlmacenadas")
    String administrarCitasAlmacenadas(Model model)throws Exception {
        model.addAttribute("listaCitas",citaService.listaCitas(false));
        model.addAttribute("cantidadCitas",citaService.cantCitas());
        return "citasAlmacenadasAdmin";
    }
}