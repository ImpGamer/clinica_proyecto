package com.imp.clinica.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CITAS_AGENDADAS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitaAgendada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private Cliente cliente;
    @OneToOne
    @JoinColumn(name = "cita_id")
    private Cita cita_id;
    public CitaAgendada(Cliente cliente,Cita cita) {
        this.cliente = cliente;
        this.cita_id = cita;
    }
}