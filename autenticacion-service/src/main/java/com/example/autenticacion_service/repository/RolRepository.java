package com.example.autenticacion_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.autenticacion_service.model.Rol;
import com.example.autenticacion_service.model.TipoRol;

public interface RolRepository extends JpaRepository<Rol, Long> {

    Optional<Rol> findByNombre(TipoRol nombre);
    
}
