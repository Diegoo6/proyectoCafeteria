package com.cafeteria.inventario_service.repository;

import com.cafeteria.inventario_service.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventarioRepository
        extends JpaRepository<Inventario, Long> {

    // 🔎 Buscar inventario por productoId
    Optional<Inventario> findByProductoId(Long productoId);

    // 🔥 Validar si ya existe inventario para un producto
    boolean existsByProductoId(Long productoId);
}