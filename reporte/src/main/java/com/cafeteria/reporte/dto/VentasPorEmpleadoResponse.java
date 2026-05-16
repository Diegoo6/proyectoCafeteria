package com.cafeteria.reporte.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class VentasPorEmpleadoResponse {

    private String empleado;

    private Integer cantidadVentas;

    private Double totalVendido;

}


