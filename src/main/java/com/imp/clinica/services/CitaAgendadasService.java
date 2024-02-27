package com.imp.clinica.services;

import com.imp.clinica.entities.CitaAgendada;
import java.util.List;

public interface CitaAgendadasService {
    List<CitaAgendada> mostrarCitasAgendadas();
    CitaAgendada buscarCitaAgendada_ID(Long id);
    void eliminarCitaAgendada(Long id);
    void crearCitaAgendada(CitaAgendada citaAgendada);
}