package com.example.empleado_service.dto;

import java.time.LocalDate;

import com.example.empleado_service.model.TipoCargo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoRequest {

    @NotBlank (message = "Debe ingresar un nombre")
    private String nombre;

    @NotBlank (message = "Debe ingresar un apellido")
    private String apellido;

    @NotBlank (message = "Debe ingresar un telefono")
    private String telefono;

    @Email (message = "Ejemplo: nombre@algo.com")
    @NotBlank (message = "Debe ingresar correo")
    private String correo;

    @NotNull (message = "Debe ingresar fecha")
    private LocalDate fechaNacimiento;

    @NotNull (message = "Debe asignar un cargo")
    private TipoCargo tipoCargo;

    @NotNull (message = "Ingrese id de usuario")
    private Long usuarioId;
    
}
