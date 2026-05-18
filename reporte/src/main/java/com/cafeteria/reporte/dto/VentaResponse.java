package com.cafeteria.reporte.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class VentaResponse {

    private Long id;
    private LocalDate fechaVenta;
    private Double total;
    private String metodoDePago;
    private String observacion;
    private Long empleadoId;
    private List<DetalleVentaResponse> detalles;

    

}
