package com.example.empleado_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.empleado_service.model.Cargo;
import com.example.empleado_service.model.TipoCargo;

public interface CargoRepository extends JpaRepository<Cargo, Long> {
    Optional<Cargo> findByNombre(TipoCargo nombre);
    
}
