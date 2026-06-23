package com.example.empleado_service.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import org.springframework.hateoas.EntityModel;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;


@Tag(name = "Empleado", description = "Operaciones relacionadas con la gestion de empleados")
@RestController
@Validated
@RequestMapping("/api/v1/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @Operation(summary = "Agregar empleado", description = "Registra un nuevo empleado en el sistema")
    @PostMapping
    public ResponseEntity<EmpleadoResponse> agregarEmpleado(@Valid @RequestBody EmpleadoRequest request, @RequestHeader("Authorization") String authorizationHeader) {
        EmpleadoResponse empleadoNuevo = empleadoService.agregarEmpleado(request, authorizationHeader);
        
        URI location = URI.create("/api/v1/empleados/" + empleadoNuevo.getId());
        return ResponseEntity.created(location).body(empleadoNuevo);
    }

    @Operation(summary = "Listar empleados", description = "Obtiene la lista completa de empleados")
    @GetMapping
    public ResponseEntity<List<EntityModel<EmpleadoResponse>>> listarEmpleados() {
        List<EmpleadoResponse> listaEmpleados = empleadoService.listarEmpleados();

        List<EntityModel<EmpleadoResponse>> listarEmpleadosConLinks = listaEmpleados.stream()
                                                                      .map(empleado -> EntityModel.of(empleado,
                                                                      linkTo(methodOn(EmpleadoController.class)
                                                                      .buscarPorId(empleado.getId())).withSelfRel(),

                                                                      linkTo(methodOn(EmpleadoController.class)
                                                                      .modificarPorId(empleado.getId(), null)).withRel("modificar-empleado"),

                                                                      linkTo(methodOn(EmpleadoController.class)
                                                                      .modificarCargo(empleado.getId(), null)).withRel("modificar-cargo"),

                                                                      linkTo(methodOn(EmpleadoController.class)
                                                                      .eliminarEmpleado(empleado.getId())).withRel("eliminar-empleado")
                                                                      )).toList();

        return ResponseEntity.ok(listarEmpleadosConLinks);
    }

    @Operation(summary = "Listar por cargo", description = "Obtiene una lista de empleados segun el cargo")
    @GetMapping("/cargo")
    public ResponseEntity<List<EntityModel<EmpleadoResponse>>> listarPorCargo(@RequestParam("cargo") TipoCargo tipoCargo) {
        List<EmpleadoResponse> listarPorCargo = empleadoService.listarPorCargo(tipoCargo);

        List<EntityModel<EmpleadoResponse>> listaCargoConLinks = listarPorCargo.stream()
                                                                 .map(empleado -> EntityModel.of(empleado,
                                                                 linkTo(methodOn(EmpleadoController.class)
                                                                 .buscarPorId(empleado.getId())).withSelfRel(),

                                                                 linkTo(methodOn(EmpleadoController.class)
                                                                 .modificarPorId(empleado.getId(), null)).withRel("modificar-empleado"),

                                                                 linkTo(methodOn(EmpleadoController.class)
                                                                 .modificarCargo(empleado.getId(), null)).withRel("modificar-cargo")
                                                                 )).toList();

        return ResponseEntity.ok(listaCargoConLinks);   
    }

    @Operation(summary = "Buscar por ID", description = "Obtiene los datos de un empleado segun su ID")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<EmpleadoResponse>> buscarPorId(@PathVariable("id") Long id) {
        EmpleadoResponse empleadoEncontrado = empleadoService.buscarPorId(id);

        EntityModel<EmpleadoResponse> empleadoConLink = EntityModel.of(empleadoEncontrado,
                                                        linkTo(methodOn(EmpleadoController.class)
                                                        .buscarPorId(id)).withSelfRel(),

                                                        linkTo(methodOn(EmpleadoController.class)
                                                        .listarEmpleados()).withRel("listar-todos-empleados"),
                                                        
                                                        linkTo(methodOn(EmpleadoController.class)
                                                        .modificarPorId(id, null)).withRel("modificar-empleado"),

                                                        linkTo(methodOn(EmpleadoController.class)
                                                        .modificarCargo(id, null)).withRel("modificar-cargo"),

                                                        linkTo(methodOn(EmpleadoController.class)
                                                        .eliminarEmpleado(id)).withRel("eliminar-empleado"));

        return ResponseEntity.ok(empleadoConLink);
    }

    @Operation(summary = "Modificar por ID", description = "Actualiza los datos de un empleado existente")
    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoResponse> modificarPorId(@PathVariable("id") Long id, @Valid @RequestBody EmpleadoModificar request) {
        EmpleadoResponse empleadoModificar = empleadoService.modificarEmpleadoPorId(id, request);

        return ResponseEntity.ok(empleadoModificar);
    }

    @Operation(summary = "Modificar cargo", description = "Actualiza el cargo de un empleado existente")
    @PutMapping("/{id}/cargo")
    public ResponseEntity<EmpleadoResponse> modificarCargo(@PathVariable("id") Long id,@Valid @RequestBody EmpleadoNuevoCargo request) {
        EmpleadoResponse nuevoCargo = empleadoService.modificarCargo(id, request.getTipoCargo());
        
        return ResponseEntity.ok(nuevoCargo);
    }

    @Operation(summary = "Eliminar empleado", description = "Elimina un empleado segun su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable("id") Long id) {
        empleadoService.eliminarEmpleadoPorId(id);

        return ResponseEntity.noContent().build();
    }
}
