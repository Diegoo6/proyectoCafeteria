package com.cafeteria.reporte.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class DetalleVentaResponse {

    private Long productoId;

    private Integer cantidad;

    private Double precioUnitario;

    private Double subtotal;

}
