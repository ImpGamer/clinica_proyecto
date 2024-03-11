package com.imp.clinica.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@Table(name = "CITAS")
@AllArgsConstructor
@NoArgsConstructor
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,length = 20)
    private String tratamiento;
    @Column(nullable = false)
    private LocalDateTime fechaCita;
    @Column
    private boolean disponible;
    @ManyToOne
    @JoinColumn(name = "medico_id")
    private Medico medico_id;
    @PrePersist
    void inciarCita() {
        this.disponible = true;
    }
    public Cita(String tratamiento,LocalDateTime fechaCita,Medico medico) {
        this.tratamiento = tratamiento;
        this.fechaCita = fechaCita;
        this.medico_id = medico;
    }
    @Override
    public String toString() {
        return "Tratamiento: "+this.tratamiento+"\nFecha Cita: "+this.fechaCita+"\nDoctor: "+this.medico_id.getNombre();
    }
}
