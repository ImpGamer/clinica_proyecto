package com.imp.clinica.services;

import com.imp.clinica.entities.Cita;
import java.util.List;

public interface CitaService {
    List<Cita> listaCitas(boolean disponibles)throws Exception;
    Cita buscarCita_ID(Long id);
    void eliminarCita(Long id);
    void agregarCita(Cita cita)throws Exception;
    void editarCita(Long id,Cita cita)throws Exception;
}