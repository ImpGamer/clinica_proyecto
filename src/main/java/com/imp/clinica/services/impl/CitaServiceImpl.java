package com.imp.clinica.services.impl;

import com.imp.clinica.entities.Cita;
import com.imp.clinica.repositories.CitaRepository;
import com.imp.clinica.services.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CitaServiceImpl implements CitaService {
    @Autowired
    private CitaRepository citaRepository;
    @Override
    public List<Cita> listaCitas(boolean disponibles)throws Exception {
        List<Cita> citasDisponibles = new ArrayList<>();

        if(disponibles) {
            for(Cita cita:citaRepository.findAll()) {
                if(cita.isDisponible()) {
                    citasDisponibles.add(cita);
                }
            }
            if(citasDisponibles.isEmpty()) {
                throw new Exception("No se encuentran citas disponibles. Vuelva pronto");
            }
        } else {
            citasDisponibles = citaRepository.findAll();
            if(citasDisponibles.isEmpty()) {
                throw new Exception("No se encuentra ninguna cita registrada. Vuelva pronto");
            }
        }
        return citasDisponibles;
    }

    @Override
    public Cita buscarCita_ID(Long id) {
        return citaRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarCita(Long id) {
        citaRepository.deleteById(id);
    }

    @Override
    public void agregarCita(Cita cita)throws Exception {
        LocalDateTime fechaActual = LocalDateTime.now();

        if(cita.getFechaCita().isAfter(fechaActual) && !cita.getNombre().isEmpty()) {
            citaRepository.save(cita);
        } else {
            throw new Exception("No se a colocado un titulo o la fecha de la cita es pasada\nVerifique sus datos");
        }
    }
    @Override
    public void editarCita(Long id, Cita cita)throws Exception {
        LocalDateTime fechaActual = LocalDateTime.now();
        Cita citaBBDD = citaRepository.findById(id).orElse(null);

        if(citaBBDD != null) {
            if(cita.getFechaCita().isAfter(fechaActual) && !cita.getNombre().isEmpty()) {
                citaBBDD.setNombre(cita.getNombre());
                citaBBDD.setFechaCita(cita.getFechaCita());
                citaRepository.save(citaBBDD);
            } else {
                throw new Exception("No se a colocado un titulo o la fecha de la cita es pasada\nVerifique sus datos");
            }
        }
    }
}