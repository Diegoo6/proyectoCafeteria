package com.cafeteria.reporte.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class VentasPorFechaResponse {

    private LocalDate fecha;

    private Integer cantidadVentas;

    private Double totalVendido;

}
