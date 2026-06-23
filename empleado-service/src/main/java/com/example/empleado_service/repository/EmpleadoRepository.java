package com.example.empleado_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.empleado_service.model.Empleado;
import com.example.empleado_service.model.TipoCargo;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    List<Empleado> findByCargo_TipoCargo(TipoCargo tipoCargo);
    
}
