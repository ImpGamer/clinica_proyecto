package com.imp.clinica.services.impl;

import com.imp.clinica.entities.Medico;
import com.imp.clinica.repositories.MedicoRepository;
import com.imp.clinica.services.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoServiceImpl implements MedicoService {
    @Autowired
    private MedicoRepository medicoRepository;
    private final String medicoError = "La edad del medico no puede ser menor a 18 o el nombre es demasiado corto";
    @Override
    public List<Medico> listaMedicos() {
        return medicoRepository.findAll();
    }

    @Override
    public Medico buscarMedico_ID(Long id) {
        return medicoRepository.findById(id).orElse(null);
    }

    @Override
    public List<Medico> buscarMedico_Nombre(String nombre)throws Exception {
        List<Medico> medicosNombre = medicoRepository.findMedicosByName(nombre);
        if(medicosNombre == null || medicosNombre.isEmpty()) {
            throw new Exception("No se encuentra ningun medico con el nombre '"+nombre+"'");
        }
        return medicosNombre;
    }

    @Override
    public void eliminarMedico(Long id) {
        medicoRepository.deleteById(id);
    }

    @Override
    public void modificarMedico(Long id, Medico medico)throws Exception {
        Medico medicoBBDD = medicoRepository.findById(id).orElse(null);
        if(medicoBBDD != null) {
            if(medico.getNombre().length() > 3 && medico.getEdad() >= 18) {
                medicoBBDD.setEdad(medico.getEdad());
                medicoBBDD.setNombre(medico.getNombre());
                medicoRepository.save(medicoBBDD);
            } else {
                throw new Exception(medicoError);
            }
        }
    }

    @Override
    public void agregarMedico(Medico medico)throws Exception {
        if(medico.getNombre().length() > 3 && medico.getEdad() >= 18) {
            medicoRepository.save(medico);
        } else {
            throw new Exception(medicoError);
        }
    }
}
