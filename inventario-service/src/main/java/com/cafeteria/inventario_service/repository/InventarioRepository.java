package com.cafeteria.inventario_service.repository;

import com.cafeteria.inventario_service.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {

  
    Optional<Inventario> findByProductoId(Long productoId);

   
    boolean existsByProductoId(Long productoId);
}