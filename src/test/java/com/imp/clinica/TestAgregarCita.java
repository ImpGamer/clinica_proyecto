package com.imp.clinica;

import com.imp.clinica.entities.Cita;
import com.imp.clinica.entities.CitaAgendada;
import com.imp.clinica.entities.Cliente;
import com.imp.clinica.entities.Medico;
import com.imp.clinica.repositories.CitaAgendadaRepository;
import com.imp.clinica.repositories.CitaRepository;
import com.imp.clinica.repositories.MedicoRepository;
import org.hibernate.grammars.hql.HqlParser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class TestAgregarCita {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private CitaAgendadaRepository citaAgendadaRepository;

    @Test
    void agregarCita() {
        Medico medico = entityManager.find(Medico.class,2);
        LocalDateTime dateTime = LocalDateTime.of(2025,3,29,11,40);

        Cita cita = new Cita("Podologia",dateTime,medico);
        entityManager.persist(cita);
    }
    @Test
    void agregarMedico() {
        Medico medico = medicoRepository.save(new Medico("Rodrigo Romero",22));
        Medico medico1 = medicoRepository.save(new Medico("Jose Ezequiel",32));

        assertThat(medico.getId()).isGreaterThan(0);
        assertThat(medico1.getId()).isGreaterThan(0);
    }
    @Test
    void agregarCitaAgendada() {
        Cita cita = entityManager.find(Cita.class,4);

        CitaAgendada citaAgendada = new CitaAgendada(new Cliente("Axel","Hernandez",20,"axelHer932@gmail.com"),cita);
        citaAgendadaRepository.save(citaAgendada);
    }
}
