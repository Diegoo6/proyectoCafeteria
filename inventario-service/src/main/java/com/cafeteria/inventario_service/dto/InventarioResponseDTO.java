package com.cafeteria.inventario_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class InventarioResponseDTO {

    private Long id;

    private Long productoId;

    private Integer stock;

    private Boolean disponible;

    private LocalDateTime fechaActualizacion;
}