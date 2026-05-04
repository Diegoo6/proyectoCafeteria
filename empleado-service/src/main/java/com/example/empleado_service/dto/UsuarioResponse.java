package com.example.empleado_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponse {

    private Long id;
    private String username;
    private String rol;
    
}
