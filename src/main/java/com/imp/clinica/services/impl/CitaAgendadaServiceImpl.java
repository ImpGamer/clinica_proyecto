package com.imp.clinica.services.impl;

import com.imp.clinica.entities.Cita;
import com.imp.clinica.entities.CitaAgendada;
import com.imp.clinica.repositories.CitaAgendadaRepository;
import com.imp.clinica.repositories.CitaRepository;
import com.imp.clinica.services.CitaAgendadasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitaAgendadaServiceImpl implements CitaAgendadasService {
    @Autowired
    private CitaAgendadaRepository citaAgendadaRepository;
    @Autowired
    private CitaRepository citaRepository;
    @Override
    public List<CitaAgendada> mostrarCitasAgendadas() {
        return citaAgendadaRepository.findAll();
    }

    @Override
    public CitaAgendada buscarCitaAgendada_ID(Long id) {
        return citaAgendadaRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarCitaAgendada(Long id) {
        CitaAgendada citaAgendadaBBDD = buscarCitaAgendada_ID(id);
        Cita citaBBDD = citaRepository.findById(citaAgendadaBBDD.getCita_id().getId()).orElse(null);

        if(citaBBDD != null) {
            citaBBDD.setDisponible(true);
            citaRepository.save(citaBBDD);
        }
        citaAgendadaRepository.deleteById(id);
    }

    @Override
    public void crearCitaAgendada(CitaAgendada citaAgendada)throws Exception {
        Cita citaBBDD = citaRepository.findById(citaAgendada.getCita_id().getId()).orElse(null);
        if(citaBBDD != null) {
            citaBBDD.setDisponible(false);
            citaRepository.save(citaBBDD);
            citaAgendadaRepository.save(citaAgendada);
        } else {
            throw new Exception("Ha ocurrido un error al tratar de guardar la cita. Intente mas tarde o contactenos");
        }
    }


    @Override
    public void almacenarDatos(CitaAgendada citaAgendada) throws Exception {
        if(citaAgendada.getCliente().getNombre().length() < 3 || citaAgendada.getCliente().getApellido().length() < 3) {
            throw new Exception("El nombre del cliente no es valido");
        }
        if(citaAgendada.getCliente().getEdad() < 1) {
            throw new Exception("La edad no puede ser menor a 1 año");
        }
        if(citaAgendada.getCita_id() == null) {
            throw new Exception("No se a seleccionado una cita");
        }
    }

    @Override
    public Long cantidadCitasAgendadas() {
        return citaAgendadaRepository.count();
    }
}
