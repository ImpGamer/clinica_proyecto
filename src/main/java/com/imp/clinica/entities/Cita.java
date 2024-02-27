package com.imp.clinica.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "TABLAS")
@AllArgsConstructor
@NoArgsConstructor
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,length = 20)
    private String nombre;
    @Column(nullable = false)
    private LocalDateTime fechaCita;
    @Column
    private boolean disponible;
}
