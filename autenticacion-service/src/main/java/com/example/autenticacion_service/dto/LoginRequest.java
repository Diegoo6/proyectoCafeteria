package com.example.autenticacion_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotBlank (message = "Debe ingresar username")
    private String username;

    @NotBlank (message = "Debe ingresar password")
    private String password;
    
}
