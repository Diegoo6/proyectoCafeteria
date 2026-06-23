package com.cafeteria.venta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cafeteria.venta.model.DetalleVenta;

@Repository

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long>{

}
