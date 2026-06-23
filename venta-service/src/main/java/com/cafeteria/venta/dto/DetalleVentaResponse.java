package com.cafeteria.venta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class DetalleVentaResponse {

    private Long productoId;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;

}
