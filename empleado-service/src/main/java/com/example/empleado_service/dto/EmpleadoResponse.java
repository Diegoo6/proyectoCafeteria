package com.example.empleado_service.dto;

import java.time.LocalDate;

import com.example.empleado_service.model.TipoCargo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmpleadoResponse {

    private final Long id;
    private final String nombre;
    private final String apellido;
    private final String telefono;
    private final String correo;
    private final LocalDate fechaNacimiento;
    private final TipoCargo tipoCargo;
    private final Long usuarioId;
    
}
