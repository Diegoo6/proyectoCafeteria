package com.example.autenticacion_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank (message = "Debe ingresar username")
    @Size (min = 5, message = "Username debe tener al menos 5 caracteres")
    private String username;

    @NotBlank (message = "Debe ingresar password")
    @Size (min = 5, message = "Password debe tener al menos 5 caracteres")
    private String password;
    
}
