package com.imp.clinica.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inicio")
public class AppController {
    @GetMapping
    String inicio() {
        return "index";
    }
    @GetMapping("/sobreNosotros")
    String sobreNosotros() {
        return "aboutUs";
    }
}