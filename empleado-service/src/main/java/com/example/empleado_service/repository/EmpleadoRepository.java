package com.example.empleado_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.empleado_service.model.Cargo;
import com.example.empleado_service.model.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    List<Empleado> findByCargo(Cargo cargo);
    
}
