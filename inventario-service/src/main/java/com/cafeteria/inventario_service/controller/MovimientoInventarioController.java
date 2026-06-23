package com.cafeteria.inventario_service.controller;

import com.cafeteria.inventario_service.dto.MovimientoInventarioRequestDTO;
import com.cafeteria.inventario_service.dto.MovimientoInventarioResponseDTO;
import com.cafeteria.inventario_service.service.MovimientoInventarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(name = "Movimientos de Inventario", description = "Operaciones relacionadas con entradas y salidas de inventario")
@RestController
@RequestMapping("api/v1/movimientos")
@RequiredArgsConstructor
public class MovimientoInventarioController {

    private final MovimientoInventarioService movimientoService;

    @Operation(summary = "Listar movimientos")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<MovimientoInventarioResponseDTO>> listar() {

        List<MovimientoInventarioResponseDTO> movimientos = movimientoService.listar();

        movimientos.forEach(movimiento -> {

            movimiento.add(
                    linkTo(
                            methodOn(MovimientoInventarioController.class)
                                    .obtener(movimiento.getId())
                    ).withSelfRel()
            );

            movimiento.add(
                    linkTo(
                            methodOn(InventarioController.class)
                                    .obtener(movimiento.getInventarioId())
                    ).withRel("inventario")
            );
        });

        return ResponseEntity.ok(movimientos);
    }

    @Operation(summary = "Buscar movimiento por ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Movimiento encontrado"),
                   @ApiResponse(responseCode = "404", description = "Movimiento no encontrado")})
    @GetMapping("/{id}")
    public ResponseEntity<MovimientoInventarioResponseDTO> obtener(@PathVariable Long id) {

        MovimientoInventarioResponseDTO movimiento = movimientoService.obtenerPorId(id);

        movimiento.add(
                linkTo(
                        methodOn(MovimientoInventarioController.class)
                                .obtener(id)
                ).withSelfRel()
        );

        movimiento.add(
                linkTo(
                        methodOn(InventarioController.class)
                                .obtener(movimiento.getInventarioId())
                ).withRel("inventario")
        );

        movimiento.add(
                linkTo(
                        methodOn(MovimientoInventarioController.class)
                                .listar()
                ).withRel("movimientos")
        );

        return ResponseEntity.ok(movimiento);
    }

    @Operation(summary = "Registrar movimiento de inventario")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Movimiento registrado correctamente"),
                   @ApiResponse(responseCode = "400", description = "Datos inválidos")})
    @PostMapping
    public ResponseEntity<MovimientoInventarioResponseDTO> crear(@Valid @RequestBody MovimientoInventarioRequestDTO dto) {

        MovimientoInventarioResponseDTO movimiento = movimientoService.guardar(dto);

        
            movimiento.add(
                    linkTo(
                            methodOn(MovimientoInventarioController.class)
                                    .obtener(movimiento.getId())
                    ).withSelfRel()
            );

            movimiento.add(
                    linkTo(
                            methodOn(InventarioController.class)
                                    .obtener(movimiento.getInventarioId())
                    ).withRel("inventario")
            );

            movimiento.add(
                    linkTo(
                            methodOn(MovimientoInventarioController.class)
                                    .listar()
                    ).withRel("movimientos")
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(movimiento);
    }

    @Operation(summary = "Eliminar movimiento")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Movimiento eliminado"),
                   @ApiResponse(responseCode = "404", description = "Movimiento no encontrado")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        movimientoService.eliminar(id);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar movimientos por inventario")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Movimientos encontrados"),
                   @ApiResponse(responseCode = "404", description = "Inventario no encontrado")})
    @GetMapping("/inventario/{inventarioId}")
    public ResponseEntity<List<MovimientoInventarioResponseDTO>>
    listarPorInventario(@PathVariable Long inventarioId) {

        List<MovimientoInventarioResponseDTO> movimientos = movimientoService.listarPorInventario(inventarioId);

        movimientos.forEach(movimiento -> {

            movimiento.add(
                    linkTo(
                            methodOn(MovimientoInventarioController.class)
                                    .obtener(movimiento.getId())
                    ).withSelfRel()
            );

            movimiento.add(
                    linkTo(
                            methodOn(InventarioController.class)
                                    .obtener(movimiento.getInventarioId())
                    ).withRel("inventario")
            );

            movimiento.add(
                    linkTo(
                            methodOn(MovimientoInventarioController.class)
                                    .listar()
                    ).withRel("movimientos")
            );
        });

        return ResponseEntity.ok(movimientos);
    }

    }
