package com.imp.clinica.services;

import com.imp.clinica.entities.Cita;
import java.util.List;

public interface CitaService {
    List<Cita> listaCitas();
    Cita buscarCita_ID(Long id);
    void eliminarCita(Long id);
    void agregarCita(Long id);
    void editarCita(Long id,Cita cita);
}