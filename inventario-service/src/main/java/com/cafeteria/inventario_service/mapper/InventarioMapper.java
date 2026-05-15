package com.cafeteria.inventario_service.mapper;

import com.cafeteria.inventario_service.dto.InventarioRequestDTO;
import com.cafeteria.inventario_service.dto.InventarioResponseDTO;
import com.cafeteria.inventario_service.model.Inventario;

public class InventarioMapper {

    // 🔄 DTO Request → Entity
    public static Inventario toEntity(InventarioRequestDTO dto) {

        Inventario inventario = new Inventario();

        inventario.setProductoId(dto.getProductoId());
        inventario.setStock(dto.getStock());
        inventario.setDisponible(dto.getDisponible());

        return inventario;
    }

    // 🔄 Entity → DTO Response
    public static InventarioResponseDTO toResponse(Inventario inventario) {

        InventarioResponseDTO dto = new InventarioResponseDTO();

        dto.setId(inventario.getId());
        dto.setProductoId(inventario.getProductoId());
        dto.setStock(inventario.getStock());
        dto.setDisponible(inventario.getDisponible());
        dto.setFechaActualizacion(inventario.getFechaActualizacion());

        return dto;
    }
}