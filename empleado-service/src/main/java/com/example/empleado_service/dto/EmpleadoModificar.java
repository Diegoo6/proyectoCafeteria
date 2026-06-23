package com.example.empleado_service.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoModificar {

    @NotBlank (message = "Debe ingresar un nombre")
    @Size (max = 50)
    private String nombre;

    @NotBlank (message = "Debe ingresar un apellido")
    @Size (max = 50)
    private String apellido;

    @NotBlank (message = "Debe ingresar un telefono")
    @Size (max = 20)
    private String telefono;

    @NotBlank (message = "Debe ingresar correo")
    @Email (message = "Ejemplo: nombre@algo.com ")
    @Size (max = 100)
    private String correo;

    @NotNull (message = "Debe ingresar fecha nacimiento")
    private LocalDate fechaNacimiento;
    
}
