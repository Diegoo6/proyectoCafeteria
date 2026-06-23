package com.cafeteria.inventario_service.controller;

import com.cafeteria.inventario_service.dto.InventarioRequestDTO;
import com.cafeteria.inventario_service.dto.InventarioResponseDTO;
import com.cafeteria.inventario_service.service.InventarioService;
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


@Tag(name = "Inventarios", description = "Operaciones relacionadas con la gestión de inventario")
@RestController
@RequestMapping("api/v1/inventarios")
@RequiredArgsConstructor
public class InventarioController {


    private final InventarioService inventarioService;

    @Operation(summary = "Listar inventarios")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<InventarioResponseDTO>> listar() {

        List<InventarioResponseDTO> inventarios = inventarioService.listar();

        inventarios.forEach(inventario -> {

            inventario.add(
                    linkTo(
                            methodOn(InventarioController.class)
                                    .obtener(inventario.getId())
                    ).withSelfRel()
            );

            inventario.add(
                    linkTo(
                            methodOn(MovimientoInventarioController.class)
                                    .listarPorInventario(inventario.getId())
                    ).withRel("movimientos")
            );
        });

        return ResponseEntity.ok(inventarios);
    }

    @Operation(summary = "Buscar inventario por ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Inventario encontrado"),
                   @ApiResponse(responseCode = "404", description = "Inventario no encontrado")})
    @GetMapping("/{id}")
    public ResponseEntity<InventarioResponseDTO> obtener(@PathVariable Long id) {

        InventarioResponseDTO inventario = inventarioService.obtenerPorId(id);

        inventario.add(
                linkTo(
                        methodOn(InventarioController.class)
                                .obtener(id)
                ).withSelfRel()
        );

        inventario.add(
                linkTo(
                        methodOn(InventarioController.class)
                                .listar()
                ).withRel("inventarios")
        );

        inventario.add(
                linkTo(
                        methodOn(MovimientoInventarioController.class)
                                .listarPorInventario(id)
                ).withRel("movimientos")
        );

        return ResponseEntity.ok(inventario);
    }

    @Operation(summary = "Crear inventario")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Inventario creado correctamente"),
                   @ApiResponse(responseCode = "400", description = "Datos inválidos")})
    @PostMapping
    public ResponseEntity<InventarioResponseDTO> crear(@Valid @RequestBody InventarioRequestDTO dto) {

        InventarioResponseDTO inventario = inventarioService.guardar(dto);
        
        inventario.add(
                linkTo(
                        methodOn(InventarioController.class)
                                .obtener(inventario.getId())
                ).withSelfRel()
        );

        inventario.add(
                linkTo(
                        methodOn(InventarioController.class)
                                .listar()
                ).withRel("inventarios")
        );

        inventario.add(
                linkTo(
                        methodOn(MovimientoInventarioController.class)
                                .listarPorInventario(inventario.getId())
                ).withRel("movimientos")
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(inventario);
    }

    @Operation(summary = "Actualizar inventario")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Inventario actualizado"),
                   @ApiResponse(responseCode = "404", description = "Inventario no encontrado")})
    @PutMapping("/{id}")
    public ResponseEntity<InventarioResponseDTO> actualizar(@PathVariable Long id,@Valid @RequestBody InventarioRequestDTO dto) {

        InventarioResponseDTO inventario = inventarioService.actualizar(id, dto);

        inventario.add(
                linkTo(
                        methodOn(InventarioController.class)
                                .obtener(inventario.getId())
                ).withSelfRel()
        );

        inventario.add(
                linkTo(
                        methodOn(InventarioController.class)
                                .listar()
                ).withRel("inventarios")
        );

        inventario.add(
                linkTo(
                        methodOn(MovimientoInventarioController.class)
                                .listarPorInventario(inventario.getId())
                ).withRel("movimientos")
        );

        return ResponseEntity.ok(inventario);
    }

    @Operation(summary = "Eliminar inventario")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Inventario eliminado"),
            @ApiResponse(responseCode = "404", description = "Inventario no encontrado")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        inventarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar inventario por ID de producto")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Inventario encontrado"),
                   @ApiResponse(responseCode = "404", description = "Inventario no encontrado")})
    @GetMapping("/producto/{productoId}")
    public ResponseEntity<InventarioResponseDTO> buscarPorProductoId(@PathVariable Long productoId) {

        InventarioResponseDTO inventario = inventarioService.buscarPorProductoId(productoId);

        inventario.add(
                linkTo(
                        methodOn(InventarioController.class)
                                .obtener(inventario.getId())
                ).withSelfRel()
        );

        inventario.add(
                linkTo(
                        methodOn(InventarioController.class)
                                .listar()
                ).withRel("inventarios")
        );

        inventario.add(
                linkTo(
                        methodOn(MovimientoInventarioController.class)
                                .listarPorInventario(inventario.getId())
                ).withRel("movimientos")
        );

        return ResponseEntity.ok(inventario);
    }

    @Operation(summary = "Descontar stock de un producto")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Stock descontado correctamente"),
                   @ApiResponse(responseCode = "404", description = "Producto no encontrado")})
    @PutMapping("/descontar/{productoId}")
    public ResponseEntity<Void> descontarStock(@PathVariable Long productoId,@RequestParam Integer cantidad) {

        inventarioService.descontarStock(productoId, cantidad);
        return ResponseEntity.ok().build();
    }

    }

