package com.example.empleado_service.dto;

import com.example.empleado_service.model.TipoCargo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoNuevoCargo {

    @NotNull (message = "Debe ingresar un cargo")
    private TipoCargo tipoCargo;
    
}
