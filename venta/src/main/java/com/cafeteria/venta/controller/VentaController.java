package com.cafeteria.venta.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafeteria.venta.dto.VentaRequest;
import com.cafeteria.venta.dto.VentaResponse;
import com.cafeteria.venta.service.VentaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/ventas")

public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService){
        this.ventaService = ventaService;
    }
    

    @GetMapping
    public ResponseEntity<List<VentaResponse>> listarVentas(){
        return ResponseEntity.ok(ventaService.listarVentas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaResponse> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(ventaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<VentaResponse> guardarVenta(@Valid @RequestBody VentaRequest request){
            return ResponseEntity.ok(ventaService.guardarVenta(request));
        }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPorId(@PathVariable Long id){
        ventaService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
