package com.example.empleado_service.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.empleado_service.client.AuthClient;
import com.example.empleado_service.dto.EmpleadoModificar;
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
    private final AuthClient authClient;

    public EmpleadoService(EmpleadoRepository empleadoRepository, CargoRepository cargoRepository, EmpleadoMapper empleadoMapper, AuthClient authClient) {
        this.empleadoRepository = empleadoRepository;
        this.cargoRepository = cargoRepository;
        this.empleadoMapper = empleadoMapper;
        this.authClient = authClient;
    }

    @Transactional
    public EmpleadoResponse agregarEmpleado(EmpleadoRequest request, String authorizationHeader) {
        authClient.buscarUsuarioPorId(request.getUsuarioId(), authorizationHeader);

        Empleado nuevoEmpleado = empleadoMapper.toEntity(request);
        Cargo cargoAsignado = cargoRepository.findByTipoCargo(request.getTipoCargo()).orElseThrow(() -> new RuntimeException("Cargo inválido"));

        nuevoEmpleado.setCargo(cargoAsignado);

        Empleado empleadoGuardado = empleadoRepository.save(nuevoEmpleado);

        return empleadoMapper.toResponse(empleadoGuardado);
    }

    @Transactional (readOnly = true)
    public List<EmpleadoResponse> listarEmpleados(){
        List<Empleado> listaEmpleados = empleadoRepository.findAll();

        return listaEmpleados.stream()
                            .map(empleadoMapper::toResponse)
                            .toList();
    }


    @Transactional (readOnly = true)
    public List<EmpleadoResponse> listarPorCargo(TipoCargo tipoCargo) {
        List<Empleado> empleadosCargo = empleadoRepository.findByCargo_TipoCargo(tipoCargo);

        return empleadosCargo.stream()
                                .map(empleadoMapper::toResponse)
                                .toList();
    }

    @Transactional (readOnly = true)
    public EmpleadoResponse buscarPorId(Long id) {
        Empleado empleadoEncontrado = empleadoRepository.findById(id).orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        return empleadoMapper.toResponse(empleadoEncontrado);
    }

    @Transactional
    public EmpleadoResponse modificarEmpleadoPorId(Long id, EmpleadoModificar request) {
        Empleado empleadoModificar = empleadoRepository.findById(id).orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        empleadoMapper.modificarEmpleado(empleadoModificar, request);

        Empleado modificadoGuardado = empleadoRepository.save(empleadoModificar);

        return empleadoMapper.toResponse(modificadoGuardado);
    }

    @Transactional
    public EmpleadoResponse modificarCargo(Long id, TipoCargo tipoCargo) {
        Empleado empleadoModificar = empleadoRepository.findById(id).orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        Cargo nuevoCargo = cargoRepository.findByTipoCargo(tipoCargo).orElseThrow(() -> new RuntimeException("Cargo inválido"));

        empleadoModificar.setCargo(nuevoCargo);

        Empleado empleadoNuevoCargo = empleadoRepository.save(empleadoModificar);

        return empleadoMapper.toResponse(empleadoNuevoCargo);
    }

    @Transactional
    public void eliminarEmpleadoPorId(Long id) {
        if (!empleadoRepository.existsById(id)) {
            throw new RuntimeException("El empleado no existe");
            
        }

        empleadoRepository.deleteById(id);
    }
}
