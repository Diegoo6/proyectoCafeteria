package com.example.empleado_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.empleado_service.client.AuthClient;
import com.example.empleado_service.dto.EmpleadoRequest;
import com.example.empleado_service.dto.EmpleadoResponse;
import com.example.empleado_service.mapper.EmpleadoMapper;
import com.example.empleado_service.model.Cargo;
import com.example.empleado_service.model.Empleado;
import com.example.empleado_service.model.TipoCargo;
import com.example.empleado_service.repository.CargoRepository;
import com.example.empleado_service.repository.EmpleadoRepository;
import com.example.empleado_service.service.EmpleadoService;

@ExtendWith(MockitoExtension.class)
class EmpleadoServiceTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @Mock
    private CargoRepository cargoRepository;

    @Mock
    private EmpleadoMapper empleadoMapper;

    @Mock
    private AuthClient authClient;

    @InjectMocks
    private EmpleadoService empleadoService;

    @Test
    void agregarEmpleado_deberiaCrearEmpleadoCuandoUsuarioExisteYCargoExiste() {
        String authorizationHeader = "Bearer token-prueba";

        EmpleadoRequest request = new EmpleadoRequest(
                "Juan",
                "Perez",
                "912345678",
                "juan@correo.com",
                LocalDate.of(2000, 1, 1),
                TipoCargo.VENDEDOR,
                1L
        );

        Cargo cargoVendedor = new Cargo(1L, TipoCargo.VENDEDOR);

        Empleado empleadoSinGuardar = new Empleado();
        empleadoSinGuardar.setNombre("Juan");
        empleadoSinGuardar.setApellido("Perez");
        empleadoSinGuardar.setTelefono("912345678");
        empleadoSinGuardar.setCorreo("juan@correo.com");
        empleadoSinGuardar.setFechaNacimiento(LocalDate.of(2000, 1, 1));
        empleadoSinGuardar.setUsuarioId(1L);

        Empleado empleadoGuardado = new Empleado(
                1L,
                "Juan",
                "Perez",
                "912345678",
                "juan@correo.com",
                LocalDate.of(2000, 1, 1),
                cargoVendedor,
                1L
        );

        EmpleadoResponse responseEsperado = new EmpleadoResponse(
                1L,
                "Juan",
                "Perez",
                "912345678",
                "juan@correo.com",
                LocalDate.of(2000, 1, 1),
                TipoCargo.VENDEDOR,
                1L
        );

        when(empleadoMapper.toEntity(request)).thenReturn(empleadoSinGuardar);
        when(cargoRepository.findByTipoCargo(TipoCargo.VENDEDOR)).thenReturn(Optional.of(cargoVendedor));
        when(empleadoRepository.save(empleadoSinGuardar)).thenReturn(empleadoGuardado);
        when(empleadoMapper.toResponse(empleadoGuardado)).thenReturn(responseEsperado);

        EmpleadoResponse resultado = empleadoService.agregarEmpleado(request, authorizationHeader);

        assertEquals(1L, resultado.getId());
        assertEquals("Juan", resultado.getNombre());
        assertEquals("Perez", resultado.getApellido());
        assertEquals(TipoCargo.VENDEDOR, resultado.getTipoCargo());
        assertEquals(1L, resultado.getUsuarioId());

        verify(authClient).buscarUsuarioPorId(1L, authorizationHeader);
        verify(cargoRepository).findByTipoCargo(TipoCargo.VENDEDOR);
        verify(empleadoRepository).save(empleadoSinGuardar);
    }

    @Test
    void listarEmpleados_deberiaRetornarListaDeEmpleados() {
        Cargo cargoVendedor = new Cargo(1L, TipoCargo.VENDEDOR);
        Cargo cargoGarzon = new Cargo(2L, TipoCargo.GARZON);

        Empleado empleado1 = new Empleado(
                1L,
                "Juan",
                "Perez",
                "912345678",
                "juan@correo.com",
                LocalDate.of(2000, 1, 1),
                cargoVendedor,
                1L
        );

        Empleado empleado2 = new Empleado(
                2L,
                "Maria",
                "Lopez",
                "987654321",
                "maria@correo.com",
                LocalDate.of(1999, 5, 10),
                cargoGarzon,
                2L
        );

        EmpleadoResponse response1 = new EmpleadoResponse(
                1L,
                "Juan",
                "Perez",
                "912345678",
                "juan@correo.com",
                LocalDate.of(2000, 1, 1),
                TipoCargo.VENDEDOR,
                1L
        );

        EmpleadoResponse response2 = new EmpleadoResponse(
                2L,
                "Maria",
                "Lopez",
                "987654321",
                "maria@correo.com",
                LocalDate.of(1999, 5, 10),
                TipoCargo.GARZON,
                2L
        );

        when(empleadoRepository.findAll()).thenReturn(List.of(empleado1, empleado2));
        when(empleadoMapper.toResponse(empleado1)).thenReturn(response1);
        when(empleadoMapper.toResponse(empleado2)).thenReturn(response2);

        List<EmpleadoResponse> resultado = empleadoService.listarEmpleados();

        assertEquals(2, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombre());
        assertEquals(TipoCargo.VENDEDOR, resultado.get(0).getTipoCargo());
        assertEquals("Maria", resultado.get(1).getNombre());
        assertEquals(TipoCargo.GARZON, resultado.get(1).getTipoCargo());

        verify(empleadoRepository).findAll();
    }

    @Test
    void buscarPorId_deberiaRetornarEmpleadoCuandoExiste() {
        Cargo cargoVendedor = new Cargo(1L, TipoCargo.VENDEDOR);

        Empleado empleado = new Empleado(
                1L,
                "Juan",
                "Perez",
                "912345678",
                "juan@correo.com",
                LocalDate.of(2000, 1, 1),
                cargoVendedor,
                1L
        );

        EmpleadoResponse responseEsperado = new EmpleadoResponse(
                1L,
                "Juan",
                "Perez",
                "912345678",
                "juan@correo.com",
                LocalDate.of(2000, 1, 1),
                TipoCargo.VENDEDOR,
                1L
        );

        when(empleadoRepository.findById(1L)).thenReturn(Optional.of(empleado));
        when(empleadoMapper.toResponse(empleado)).thenReturn(responseEsperado);

        EmpleadoResponse resultado = empleadoService.buscarPorId(1L);

        assertEquals(1L, resultado.getId());
        assertEquals("Juan", resultado.getNombre());
        assertEquals("Perez", resultado.getApellido());
        assertEquals(TipoCargo.VENDEDOR, resultado.getTipoCargo());

        verify(empleadoRepository).findById(1L);
    }

    @Test
    void modificarCargo_deberiaActualizarCargoCuandoEmpleadoExiste() {
        Cargo cargoVendedor = new Cargo(1L, TipoCargo.VENDEDOR);
        Cargo cargoCocinero = new Cargo(2L, TipoCargo.COCINERO);

        Empleado empleado = new Empleado(
                1L,
                "Juan",
                "Perez",
                "912345678",
                "juan@correo.com",
                LocalDate.of(2000, 1, 1),
                cargoVendedor,
                1L
        );

        Empleado empleadoActualizado = new Empleado(
                1L,
                "Juan",
                "Perez",
                "912345678",
                "juan@correo.com",
                LocalDate.of(2000, 1, 1),
                cargoCocinero,
                1L
        );

        EmpleadoResponse responseEsperado = new EmpleadoResponse(
                1L,
                "Juan",
                "Perez",
                "912345678",
                "juan@correo.com",
                LocalDate.of(2000, 1, 1),
                TipoCargo.COCINERO,
                1L
        );

        when(empleadoRepository.findById(1L)).thenReturn(Optional.of(empleado));
        when(cargoRepository.findByTipoCargo(TipoCargo.COCINERO)).thenReturn(Optional.of(cargoCocinero));
        when(empleadoRepository.save(empleado)).thenReturn(empleadoActualizado);
        when(empleadoMapper.toResponse(empleadoActualizado)).thenReturn(responseEsperado);

        EmpleadoResponse resultado = empleadoService.modificarCargo(1L, TipoCargo.COCINERO);

        assertEquals(1L, resultado.getId());
        assertEquals("Juan", resultado.getNombre());
        assertEquals(TipoCargo.COCINERO, resultado.getTipoCargo());

        verify(empleadoRepository).findById(1L);
        verify(cargoRepository).findByTipoCargo(TipoCargo.COCINERO);
        verify(empleadoRepository).save(empleado);
    }
}