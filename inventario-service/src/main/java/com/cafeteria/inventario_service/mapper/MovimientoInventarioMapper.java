package com.cafeteria.inventario_service.mapper;

import com.cafeteria.inventario_service.dto.MovimientoInventarioRequestDTO;
import com.cafeteria.inventario_service.dto.MovimientoInventarioResponseDTO;
import com.cafeteria.inventario_service.model.Inventario;
import com.cafeteria.inventario_service.model.MovimientoInventario;

public class MovimientoInventarioMapper {

    // 🔄 DTO Request → Entity
    public static MovimientoInventario toEntity(
            MovimientoInventarioRequestDTO dto,
            Inventario inventario) {

        MovimientoInventario movimiento = new MovimientoInventario();

        movimiento.setInventario(inventario);
        movimiento.setTipo(dto.getTipo());
        movimiento.setCantidad(dto.getCantidad());
        movimiento.setMotivo(dto.getMotivo());

        return movimiento;
    }

    // 🔄 Entity → DTO Response
    public static MovimientoInventarioResponseDTO toResponse(
            MovimientoInventario movimiento) {

        MovimientoInventarioResponseDTO dto =
                new MovimientoInventarioResponseDTO();

        dto.setId(movimiento.getId());
        dto.setInventarioId(movimiento.getInventario().getId());
        dto.setTipo(movimiento.getTipo());
        dto.setCantidad(movimiento.getCantidad());
        dto.setMotivo(movimiento.getMotivo());
        dto.setFecha(movimiento.getFecha());

        return dto;
    }
}