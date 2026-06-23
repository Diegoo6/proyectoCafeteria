package com.example.autenticacion_service.dto;

import com.example.autenticacion_service.model.TipoRol;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NuevoRolRequest {

    @NotNull (message = "Debe indicar el nuevo rol")
    private TipoRol nuevoRol;
    
}
