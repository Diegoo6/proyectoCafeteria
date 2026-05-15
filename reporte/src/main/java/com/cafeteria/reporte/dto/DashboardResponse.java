package com.cafeteria.reporte.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class DashboardResponse {

    private Integer ventasHoy;
    
    private Double totalHoy;

    private String productoMasVendido;

    private Integer ventasPendientes;

}
