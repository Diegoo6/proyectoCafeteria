package com.example.autenticacion_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.autenticacion_service.model.TipoRol;
import com.example.autenticacion_service.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    List<Usuario> findByRolNombre(TipoRol rol);
    
}
