package com.imp.clinica.repositories;

import com.imp.clinica.entities.CitaAgendada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitaAgendadaRepository extends JpaRepository<CitaAgendada,Long> {
}
