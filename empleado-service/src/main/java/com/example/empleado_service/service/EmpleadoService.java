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

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
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
        Cargo cargoAsignado = cargoRepository.findByTipoCargo(request.getTipoCargo())
                                            .orElseThrow(() -> {log.warn("Cargo {} no encontrado.", request.getTipoCargo());
                                            return new RuntimeException("Cargo inválido");});

        nuevoEmpleado.setCargo(cargoAsignado);

        Empleado empleadoGuardado = empleadoRepository.save(nuevoEmpleado);

        log.info("Empleado guardado correctamente");
        return empleadoMapper.toResponse(empleadoGuardado);
    }

    @Transactional(readOnly = true)
    public List<EmpleadoResponse> listarEmpleados(){
        List<Empleado> listaEmpleados = empleadoRepository.findAll();

        log.info("Listando empleados. {} encontrados", listaEmpleados.size());
        return listaEmpleados.stream()
                            .map(empleadoMapper::toResponse)
                            .toList();
    }


    @Transactional(readOnly = true)
    public List<EmpleadoResponse> listarPorCargo(TipoCargo tipoCargo) {
        List<Empleado> empleadosCargo = empleadoRepository.findByCargo_TipoCargo(tipoCargo);

        log.info("Listando empleados. {} encontrados", empleadosCargo.size());
        return empleadosCargo.stream()
                                .map(empleadoMapper::toResponse)
                                .toList();
    }

    @Transactional(readOnly = true)
    public EmpleadoResponse buscarPorId(Long id) {
        Empleado empleadoEncontrado = empleadoRepository.findById(id).orElseThrow(()
                                     -> {log.warn("Empleado con id {} no encontrado.", id);
                                    return new RuntimeException("Empleado no encontrado");});

        log.info("Empleado encontrado.");
        return empleadoMapper.toResponse(empleadoEncontrado);
    }

    @Transactional
    public EmpleadoResponse modificarEmpleadoPorId(Long id, EmpleadoModificar request) {
        Empleado empleadoModificar = empleadoRepository.findById(id).orElseThrow(()
                                    -> {log.warn("Empleado con id {} no encontrado.", id);
                                    return new RuntimeException("Empleado no encontrado");});

        empleadoMapper.modificarEmpleado(empleadoModificar, request);

        Empleado modificadoGuardado = empleadoRepository.save(empleadoModificar);

        log.info("Empleado con id {} modificado correctamente", id);
        return empleadoMapper.toResponse(modificadoGuardado);
    }

    @Transactional
    public EmpleadoResponse modificarCargo(Long id, TipoCargo tipoCargo) {
        Empleado empleadoModificar = empleadoRepository.findById(id).orElseThrow(()
                                    -> {log.warn("Empleado con id {} no encontrado.", id);
                                    return new RuntimeException("Empleado no encontrado");});

        Cargo nuevoCargo = cargoRepository.findByTipoCargo(tipoCargo).orElseThrow(()
                        -> {log.warn("Cargo {} no encontrado." ,tipoCargo);
                        return new RuntimeException("Cargo inválido");});

        empleadoModificar.setCargo(nuevoCargo);

        Empleado empleadoNuevoCargo = empleadoRepository.save(empleadoModificar);

        log.info("Cargo del empleado con id {} modificado correctamente", id);
        return empleadoMapper.toResponse(empleadoNuevoCargo);
    }

    @Transactional
    public void eliminarEmpleadoPorId(Long id) {
        if (!empleadoRepository.existsById(id)) {
            log.warn("Empleado con id {} no encontrado.", id);
            throw new RuntimeException("El empleado no existe");
            
        }

        log.info("Empleado con id {} eliminado correctamente", id);
        empleadoRepository.deleteById(id);
    }
}
