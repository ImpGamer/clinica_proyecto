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
    @ManyToOne
    @JoinColumn(name = "medico_id")
    private Medico medico_id;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente_id;
    @OneToOne
    @JoinColumn(name = "cita_id")
    private Cita cita_id;
}