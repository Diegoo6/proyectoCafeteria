package com.cafeteria.inventario_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Schema(description = "DTO para registrar movimientos de inventario")
public class MovimientoInventarioRequestDTO {

    @Schema(description = "ID del inventario afectado",
            example = "1")
    @NotNull(message = "El inventarioId es obligatorio")
    private Long inventarioId;

    @Schema(description = "Tipo de movimiento",
            example = "ENTRADA")
    @NotBlank(message = "El tipo es obligatorio")
    private String tipo;

    @Schema(description = "Cantidad de unidades afectadas",
            example = "10")
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    private Integer cantidad;

    @Schema(description = "Motivo del movimiento",
            example = "Compra a proveedor")
    @NotBlank(message = "El motivo es obligatorio")
    private String motivo;

}