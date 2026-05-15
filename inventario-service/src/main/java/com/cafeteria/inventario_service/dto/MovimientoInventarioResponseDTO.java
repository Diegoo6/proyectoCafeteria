package com.cafeteria.inventario_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MovimientoInventarioResponseDTO {

    private Long id;

    private Long inventarioId;

    private String tipo;

    private Integer cantidad;

    private String motivo;

    private LocalDateTime fecha;
}