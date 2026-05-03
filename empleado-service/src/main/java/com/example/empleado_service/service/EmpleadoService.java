package com.example.empleado_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.empleado_service.dto.EmpleadoRequest;
import com.example.empleado_service.dto.EmpleadoResponse;
import com.example.empleado_service.mapper.EmpleadoMapper;
import com.example.empleado_service.model.Cargo;
import com.example.empleado_service.model.Empleado;
import com.example.empleado_service.model.TipoCargo;
import com.example.empleado_service.repository.CargoRepository;
import com.example.empleado_service.repository.EmpleadoRepository;

@Service
public class EmpleadoService {
    
    private final EmpleadoRepository empleadoRepository;
    private final CargoRepository cargoRepository;
    private final EmpleadoMapper empleadoMapper;

    public EmpleadoService(EmpleadoRepository empleadoRepository, CargoRepository cargoRepository, EmpleadoMapper empleadoMapper) {
        this.empleadoRepository = empleadoRepository;
        this.cargoRepository = cargoRepository;
        this.empleadoMapper = empleadoMapper;
    }

    public EmpleadoResponse agregarEmpleado(EmpleadoRequest request) {
        Empleado nuevoEmpleado = empleadoMapper.toEntity(request);
        Cargo cargoAsignado = cargoRepository.findByNombre(request.getTipoCargo()).orElseThrow(() -> new RuntimeException("Cargo invalido"));

        nuevoEmpleado.setCargo(cargoAsignado);

        Empleado empleadoGuardado = empleadoRepository.save(nuevoEmpleado);

        return empleadoMapper.toResponse(empleadoGuardado);
    }

    public List<EmpleadoResponse> listarEmpleados(){
        List<Empleado> listaEmpleados = empleadoRepository.findAll();

        return listaEmpleados.stream()
                            .map(empleadoMapper::toResponse)
                            .toList();
    }

    public List<EmpleadoResponse> listarPorCargo(TipoCargo tipoCargo) {
        Cargo cargo = cargoRepository.findByNombre(tipoCargo).orElseThrow(() -> new RuntimeException("No existe el cargo"));

        return empleadoRepository.findByCargo(cargo)
                                .stream()
                                .map(empleadoMapper::toResponse)
                                .toList();
    }
}
