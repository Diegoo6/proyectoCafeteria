package com.cafeteria.reporte.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ProductoMasVendidoResponse {

    private String nombreProducto;

    private Integer cantidadVendida;

    private String categoria;

}
