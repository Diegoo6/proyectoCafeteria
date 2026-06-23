package com.example.autenticacion_service.mapper;

import org.springframework.stereotype.Component;

import com.example.autenticacion_service.dto.RegisterRequest;
import com.example.autenticacion_service.dto.UsuarioResponse;
import com.example.autenticacion_service.model.Usuario;

@Component
public class UsuarioMapper {

    public Usuario toEntity(RegisterRequest request) {
        Usuario usuario = new Usuario();

        usuario.setUsername(request.getUsername());
        usuario.setPassword(request.getPassword());

        return usuario;
    }

    public UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(usuario.getId(), usuario.getUsername(), usuario.getRol().getNombre().name());
    }
    
}
