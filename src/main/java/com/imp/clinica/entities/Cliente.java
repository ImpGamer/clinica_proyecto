package com.imp.clinica.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Cliente {
    private String nombre;
    private String apellido;
    private int edad;
    private String correo;
    @Override
    public String toString() {
        return "Nombre: "+this.nombre+"\nApellido: "+this.apellido+"\nEdad: "+this.edad+"\nCorreo: "+this.correo;
    }
}