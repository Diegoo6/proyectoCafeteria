package com.cafeteria.venta.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class VentaRequest {

    @NotNull
    @Size(max= 25)
    private String metodoDePago;

    @Size(max= 225)
    private String observacion;

    @NotNull
    private Long empleadoId;

    @NotNull
    private List<DetalleVentaRequest> detalles;


}
