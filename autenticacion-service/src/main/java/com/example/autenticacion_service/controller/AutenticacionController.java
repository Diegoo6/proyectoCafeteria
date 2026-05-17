package com.example.autenticacion_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.autenticacion_service.dto.LoginRequest;
import com.example.autenticacion_service.dto.LoginResponse;
import com.example.autenticacion_service.dto.NuevoRolRequest;
import com.example.autenticacion_service.dto.RegisterRequest;
import com.example.autenticacion_service.dto.UsuarioResponse;
import com.example.autenticacion_service.model.TipoRol;
import com.example.autenticacion_service.service.AutenticacionService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;



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
    
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(autenticacionService.buscarPorId(id));
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioResponse>> listarUsuarios() {
        List<UsuarioResponse> listaUsuarios = autenticacionService.listarUsuarios();

        return ResponseEntity.ok(listaUsuarios);
    }

    @GetMapping("/usuarios/rol")
    public ResponseEntity<List<UsuarioResponse>> filtrarPorRol(@RequestParam("rol") TipoRol rol) {
        List<UsuarioResponse> listaRol = autenticacionService.listarPorRol(rol);

        return ResponseEntity.ok(listaRol);
    }
    
    
    @PutMapping("/usuarios/{id}/cambiar-rol")
    public ResponseEntity<UsuarioResponse> cambiarRol(@PathVariable("id") Long id,@Valid @RequestBody NuevoRolRequest request) {
        UsuarioResponse usuario = autenticacionService.cambiarRol(id, request);
        
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> eliminarPorId(@PathVariable("id") Long id) {
        autenticacionService.eliminarPorId(id);

        return ResponseEntity.noContent().build();
    }    
    
}
