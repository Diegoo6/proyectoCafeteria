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

import com.cafeteria.venta.model.Venta;
import com.cafeteria.venta.service.VentaService;

@RestController
@RequestMapping("/api/ventas")

public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService){
        this.ventaService = ventaService;
    }

    @GetMapping
    public ResponseEntity<List<Venta>> listarVentas(){
        return ResponseEntity.ok(ventaService.listarVentas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venta> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(ventaService.encontrarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Venta> guardarVenta(@RequestBody Venta venta){
        return ResponseEntity.ok(ventaService.guardarVenta(venta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPorId(@PathVariable Long id){
        ventaService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }


}
