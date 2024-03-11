package com.imp.clinica.repositories;

import com.imp.clinica.entities.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MedicoRepository extends JpaRepository<Medico,Long> {
    @Query("SELECT p FROM Medico p WHERE p.nombre LIKE %?1%")
    List<Medico> findMedicosByName(String name);
}