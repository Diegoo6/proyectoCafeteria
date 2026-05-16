package com.cafeteria.inventario_service.controller;

import com.cafeteria.inventario_service.dto.MovimientoInventarioRequestDTO;
import com.cafeteria.inventario_service.dto.MovimientoInventarioResponseDTO;
import com.cafeteria.inventario_service.service.MovimientoInventarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/movimientos")
@RequiredArgsConstructor
public class MovimientoInventarioController {

    private final MovimientoInventarioService movimientoService;

  
    @GetMapping
    public List<MovimientoInventarioResponseDTO> listar() {

        return movimientoService.listar();
    }

   
    @GetMapping("/{id}")
    public MovimientoInventarioResponseDTO obtener(@PathVariable Long id) {

        return movimientoService.obtenerPorId(id);
    }

  
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovimientoInventarioResponseDTO crear(@Valid @RequestBody MovimientoInventarioRequestDTO dto) {

        return movimientoService.guardar(dto);
    }

    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {

        movimientoService.eliminar(id);
    }

    
    @GetMapping("/inventario/{inventarioId}")
    public List<MovimientoInventarioResponseDTO>
    listarPorInventario(@PathVariable Long inventarioId) {

        return movimientoService
                .listarPorInventario(inventarioId);
    }
}