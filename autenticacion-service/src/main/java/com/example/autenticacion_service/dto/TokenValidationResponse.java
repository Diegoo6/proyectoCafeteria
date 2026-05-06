package com.example.autenticacion_service.dto;

import com.example.autenticacion_service.model.TipoRol;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TokenValidationResponse {

    private boolean valid;
    private String username;
    private TipoRol tipoRol;
    
}
