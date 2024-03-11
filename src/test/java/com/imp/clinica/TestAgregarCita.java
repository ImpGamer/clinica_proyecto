package com.imp.clinica;

import com.imp.clinica.entities.Cita;
import com.imp.clinica.entities.Medico;
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
    private CitaRepository citaRepository;
    @Test
    void agregarCita() {
        Medico medico = entityManager.find(Medico.class,1);
        LocalDateTime dateTime = LocalDateTime.of(2024,9,10,13,15);

        Cita cita = new Cita("Limpieza Dental",dateTime,medico);
        citaRepository.save(cita);
    }
    @Test
    void agregarMedico() {
        Medico medico = medicoRepository.save(new Medico("Rodrigo Romero",22));
        Medico medico1 = medicoRepository.save(new Medico("Jose Ezequiel",32));

        assertThat(medico.getId()).isGreaterThan(0);
        assertThat(medico1.getId()).isGreaterThan(0);
    }
}
