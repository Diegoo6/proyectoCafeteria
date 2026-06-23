package com.cafeteria.inventario_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Schema(description = "DTO para registrar o actualizar inventario")
public class InventarioRequestDTO {

    @Schema(description = "ID del producto asociado",
            example = "1")
    @NotNull(message = "El productoId es obligatorio")
    private Long productoId;

    @Schema(description = "Cantidad disponible en stock",
            example = "50")
    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    @Schema(description = "Indica si el producto está disponible para la venta",
            example = "true")
    @NotNull(message = "Debe indicar si está disponible")
    private Boolean disponible;

}
