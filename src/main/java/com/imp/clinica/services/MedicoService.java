package com.imp.clinica.services;

import com.imp.clinica.entities.Medico;
import java.util.List;

public interface MedicoService {
    List<Medico> listaMedicos();
    Medico buscarMedico_ID(Long id);
    List<Medico> buscarMedico_Nombre(String nombre)throws Exception;
    void eliminarMedico(Long id);
    void modificarMedico(Long id,Medico medico)throws Exception;
    void agregarMedico(Medico medico)throws Exception;
}
