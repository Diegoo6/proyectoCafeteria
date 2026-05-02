package com.example.autenticacion_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UsuarioResponse {

    private final Long id;
    private final String username;
    private final String rol;
    
}
