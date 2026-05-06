package com.example.empleado_service.dto;

import com.example.empleado_service.model.TipoRol;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenValidationResponse {

    private boolean valid;
    private String username;
    private TipoRol tipoRol;
    
}
