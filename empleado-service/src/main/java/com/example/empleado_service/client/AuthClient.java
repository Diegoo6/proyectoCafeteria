package com.example.empleado_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.empleado_service.dto.UsuarioResponse;

@FeignClient(name = "autenticacion-service", url = "${auth.service.url}")
public interface AuthClient {

    @GetMapping("/api/v1/autenticacion/usuarios/{id}")
       UsuarioResponse buscarUsuarioPorId(@PathVariable("id") Long id, @RequestHeader("Authorization") String authorizationHeader);
}