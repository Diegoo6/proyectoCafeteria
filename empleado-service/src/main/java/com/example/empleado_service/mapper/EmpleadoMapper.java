package com.example.empleado_service.mapper;

import org.springframework.stereotype.Component;

import com.example.empleado_service.dto.EmpleadoModificar;
import com.example.empleado_service.dto.EmpleadoRequest;
import com.example.empleado_service.dto.EmpleadoResponse;
import com.example.empleado_service.model.Empleado;

@Component
public class EmpleadoMapper {
    
    public Empleado toEntity(EmpleadoRequest request) {
        Empleado empleado = new Empleado();

        empleado.setNombre(request.getNombre());
        empleado.setApellido(request.getApellido());
        empleado.setTelefono(request.getTelefono());
        empleado.setCorreo(request.getCorreo());
        empleado.setFechaNacimiento(request.getFechaNacimiento());
        empleado.setUsuarioId(request.getUsuarioId());

        return empleado;
    }

    public EmpleadoResponse toResponse(Empleado emp) {
        return new EmpleadoResponse(emp.getId(), emp.getNombre(), emp.getApellido(), emp.getTelefono(), emp.getCorreo(), emp.getFechaNacimiento(), emp.getCargo().getTipoCargo(), emp.getUsuarioId());
    }

    public void modificarEmpleado(Empleado emp, EmpleadoModificar request) {
        emp.setNombre(request.getNombre());
        emp.setApellido(request.getApellido());
        emp.setTelefono(request.getTelefono());
        emp.setCorreo(request.getCorreo());
        emp.setFechaNacimiento(request.getFechaNacimiento());
    }
}
