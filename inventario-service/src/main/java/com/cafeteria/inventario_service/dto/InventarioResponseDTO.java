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
@Schema(description = "Respuesta de un inventario")
public class InventarioResponseDTO extends RepresentationModel<InventarioResponseDTO>{

    @Schema(example = "1")
    private Long id;

    @Schema(example = "1")
    private Long productoId;

    @Schema(example = "50")
    private Integer stock;

    @Schema(example = "true")
    private Boolean disponible;

    @Schema(example = "2026-06-17T12:30:00")
    private LocalDateTime fechaActualizacion;

}