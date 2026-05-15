package com.cafeteria.inventario_service.controller;

import com.cafeteria.inventario_service.dto.InventarioRequestDTO;
import com.cafeteria.inventario_service.dto.InventarioResponseDTO;
import com.cafeteria.inventario_service.service.InventarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventarios")
@RequiredArgsConstructor
public class InventarioController {

    private final InventarioService inventarioService;

    // 📄 LISTAR TODOS
    @GetMapping
    public List<InventarioResponseDTO> listar() {

        return inventarioService.listar();
    }

    // 🔍 OBTENER POR ID
    @GetMapping("/{id}")
    public InventarioResponseDTO obtener(
            @PathVariable Long id) {

        return inventarioService.obtenerPorId(id);
    }

    // 💾 CREAR INVENTARIO
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InventarioResponseDTO crear(
            @Valid @RequestBody InventarioRequestDTO dto) {

        return inventarioService.guardar(dto);
    }

    // ✏️ ACTUALIZAR INVENTARIO
    @PutMapping("/{id}")
    public InventarioResponseDTO actualizar(
            @PathVariable Long id,
            @Valid @RequestBody InventarioRequestDTO dto) {

        return inventarioService.actualizar(id, dto);
    }

    // 🗑️ ELIMINAR INVENTARIO
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(
            @PathVariable Long id) {

        inventarioService.eliminar(id);
    }

    // 🔎 BUSCAR POR productoId
    @GetMapping("/producto/{productoId}")
    public InventarioResponseDTO buscarPorProductoId(
            @PathVariable Long productoId) {

        return inventarioService
                .buscarPorProductoId(productoId);
    }
}