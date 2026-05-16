package com.cafeteria.inventario_service.repository;

import com.cafeteria.inventario_service.model.MovimientoInventario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimientoInventarioRepository extends JpaRepository<MovimientoInventario, Long> {

   
    List<MovimientoInventario> findByInventarioId(Long inventarioId);
}