package com.cafeteria.venta.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class VentaResponse {

    private Long id;
    private LocalDate fechaVenta;
    private Double total;
    private String estadoDePago;
    private String metodoDePago;
    private String observacion;
    private Long empleadoId;

    private List<DetalleVentaResponse> detalles;
    

}
