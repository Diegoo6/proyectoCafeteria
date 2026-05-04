package com.example.empleado_service.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.empleado_service.dto.EmpleadoModificar;
import com.example.empleado_service.dto.EmpleadoNuevoCargo;
import com.example.empleado_service.dto.EmpleadoRequest;
import com.example.empleado_service.dto.EmpleadoResponse;
import com.example.empleado_service.model.TipoCargo;
import com.example.empleado_service.service.EmpleadoService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@Validated
@RequestMapping("/api/v1/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @PostMapping
    public ResponseEntity<EmpleadoResponse> agregarEmpleado(@Valid @RequestBody EmpleadoRequest request) {
        EmpleadoResponse empleadoNuevo = empleadoService.agregarEmpleado(request);
        
        URI location = URI.create("/api/v1/empleados/" + empleadoNuevo.getId());
        return ResponseEntity.created(location).body(empleadoNuevo);
    }

    @GetMapping
    public ResponseEntity<List<EmpleadoResponse>> listarEmpleados() {
        List<EmpleadoResponse> listaEmpleados = empleadoService.listarEmpleados();

        return ResponseEntity.ok(listaEmpleados);
    }

    @GetMapping("/cargo")
    public ResponseEntity<List<EmpleadoResponse>> listarPorCargo(@RequestParam TipoCargo tipoCargo) {
        List<EmpleadoResponse> listarPorCargo = empleadoService.listarPorCargo(tipoCargo);

        return ResponseEntity.ok(listarPorCargo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoResponse> buscarPorId(@PathVariable Long id) {
        EmpleadoResponse empleadoEncontrado = empleadoService.buscarPorId(id);

        return ResponseEntity.ok(empleadoEncontrado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoResponse> modificarPorId(@PathVariable Long id, @Valid @RequestBody EmpleadoModificar request) {
        EmpleadoResponse empleadoModificar = empleadoService.modificarEmpleadoPorId(id, request);

        return ResponseEntity.ok(empleadoModificar);
    }

    @PutMapping("/{id}/cargo")
    public ResponseEntity<EmpleadoResponse> modificarCargo(@PathVariable Long id,@Valid @RequestBody EmpleadoNuevoCargo request) {
        EmpleadoResponse nuevoCargo = empleadoService.modificarCargo(id, request.getTipoCargo());
        
        return ResponseEntity.ok(nuevoCargo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable Long id) {
        empleadoService.eliminarEmpleadoPorId(id);

        return ResponseEntity.noContent().build();
    }
}
