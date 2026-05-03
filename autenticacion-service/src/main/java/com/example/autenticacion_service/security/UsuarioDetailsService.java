package com.example.autenticacion_service.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.example.autenticacion_service.model.Usuario;
import com.example.autenticacion_service.repository.UsuarioRepository;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return User.builder().username(usuario.getUsername()).password(usuario.getPassword()).authorities("ROLE_" + usuario.getRol().getNombre().name()).build();
    }
    
}
