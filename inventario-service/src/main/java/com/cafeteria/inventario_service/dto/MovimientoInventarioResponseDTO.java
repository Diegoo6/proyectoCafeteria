package com.cafeteria.inventario_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import org.springframework.hateoas.RepresentationModel;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Respuesta de un movimiento de inventario")
public class MovimientoInventarioResponseDTO extends RepresentationModel<MovimientoInventarioResponseDTO>{

    @Schema(example = "1")
    private Long id;

    @Schema(example = "1")
    private Long inventarioId;

    @Schema(example = "ENTRADA")
    private String tipo;

    @Schema(example = "10")
    private Integer cantidad;

    @Schema(example = "Compra a proveedor")
    private String motivo;

    @Schema(example = "2026-06-17T12:30:00")
    private LocalDateTime fecha;

}