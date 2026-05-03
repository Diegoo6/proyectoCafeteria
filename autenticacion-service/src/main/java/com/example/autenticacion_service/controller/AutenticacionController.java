package com.example.autenticacion_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.autenticacion_service.dto.LoginRequest;
import com.example.autenticacion_service.dto.LoginResponse;
import com.example.autenticacion_service.dto.RegisterRequest;
import com.example.autenticacion_service.dto.UsuarioResponse;
import com.example.autenticacion_service.service.AutenticacionService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@Validated
@RequestMapping("/api/v1/autenticacion")
public class AutenticacionController {

    private final AutenticacionService autenticacionService;

    public AutenticacionController(AutenticacionService autenticacionService) {
        this.autenticacionService = autenticacionService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<UsuarioResponse> registrarUsuario(@Valid @RequestBody RegisterRequest request) {
        UsuarioResponse usuarioRegistrado = autenticacionService.registrarUsuario(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRegistrado);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> usuarioLogin(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(autenticacionService.usuarioLogin(request));
    }
    
}
