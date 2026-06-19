package com.example.autenticacion_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.autenticacion_service.dto.LoginRequest;
import com.example.autenticacion_service.dto.LoginResponse;
import com.example.autenticacion_service.dto.NuevoRolRequest;
import com.example.autenticacion_service.dto.RegisterRequest;
import com.example.autenticacion_service.dto.UsuarioResponse;
import com.example.autenticacion_service.mapper.UsuarioMapper;
import com.example.autenticacion_service.model.Rol;
import com.example.autenticacion_service.model.TipoRol;
import com.example.autenticacion_service.model.Usuario;
import com.example.autenticacion_service.repository.RolRepository;
import com.example.autenticacion_service.repository.UsuarioRepository;
import com.example.autenticacion_service.security.JwtService;
import com.example.autenticacion_service.service.AutenticacionService;

@ExtendWith(MockitoExtension.class)
class AutenticacionServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RolRepository rolRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AutenticacionService autenticacionService;

    @Test
    void registrarUsuario_deberiaCrearUsuarioCuandoNoExiste() {
        RegisterRequest request = new RegisterRequest("usuarioTest", "12345");

        Rol rolEmpleado = new Rol(1L, TipoRol.EMPLEADO);

        Usuario usuarioSinGuardar = new Usuario();
        usuarioSinGuardar.setUsername("usuarioTest");
        usuarioSinGuardar.setPassword("12345");

        Usuario usuarioGuardado = new Usuario(1L, "usuarioTest", "passwordEncriptada", rolEmpleado);

        UsuarioResponse responseEsperado = new UsuarioResponse(1L, "usuarioTest", "EMPLEADO");

        when(usuarioRepository.findByUsername("usuarioTest")).thenReturn(Optional.empty());
        when(usuarioMapper.toEntity(request)).thenReturn(usuarioSinGuardar);
        when(rolRepository.findByNombre(TipoRol.EMPLEADO)).thenReturn(Optional.of(rolEmpleado));
        when(passwordEncoder.encode("12345")).thenReturn("passwordEncriptada");
        when(usuarioRepository.save(usuarioSinGuardar)).thenReturn(usuarioGuardado);
        when(usuarioMapper.toResponse(usuarioGuardado)).thenReturn(responseEsperado);

        UsuarioResponse resultado = autenticacionService.registrarUsuario(request);

        assertEquals(1L, resultado.getId());
        assertEquals("usuarioTest", resultado.getUsername());
        assertEquals("EMPLEADO", resultado.getRol());

        verify(usuarioRepository).save(usuarioSinGuardar);
    }

    @Test
    void usuarioLogin_deberiaRetornarToken() {
        LoginRequest request = new LoginRequest("admin", "admin");

        Rol rolAdmin = new Rol(1L, TipoRol.ADMIN);
        Usuario usuario = new Usuario(1L, "admin", "passwordEncriptada", rolAdmin);

        when(usuarioRepository.findByUsername("admin")).thenReturn(Optional.of(usuario));
        when(jwtService.generateToken("admin", "ADMIN")).thenReturn("token-prueba");

        LoginResponse resultado = autenticacionService.usuarioLogin(request);

        assertEquals("token-prueba", resultado.getToken());

        verify(authenticationManager).authenticate(any());
        verify(jwtService).generateToken("admin", "ADMIN");
    }

    @Test
    void buscarPorId_deberiaRetornarUsuarioCuandoExiste() {
        Rol rolEmpleado = new Rol(1L, TipoRol.EMPLEADO);
        Usuario usuario = new Usuario(1L, "usuarioTest", "password", rolEmpleado);
        UsuarioResponse responseEsperado = new UsuarioResponse(1L, "usuarioTest", "EMPLEADO");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioMapper.toResponse(usuario)).thenReturn(responseEsperado);

        UsuarioResponse resultado = autenticacionService.buscarPorId(1L);

        assertEquals(1L, resultado.getId());
        assertEquals("usuarioTest", resultado.getUsername());
        assertEquals("EMPLEADO", resultado.getRol());

        verify(usuarioRepository).findById(1L);
    }

    @Test
    void cambiarRol_deberiaActualizarRolCuandoUsuarioExiste() {
        NuevoRolRequest request = new NuevoRolRequest(TipoRol.ADMIN);

        Rol rolEmpleado = new Rol(1L, TipoRol.EMPLEADO);
        Rol rolAdmin = new Rol(2L, TipoRol.ADMIN);

        Usuario usuario = new Usuario(1L, "usuarioTest", "password", rolEmpleado);
        Usuario usuarioActualizado = new Usuario(1L, "usuarioTest", "password", rolAdmin);

        UsuarioResponse responseEsperado = new UsuarioResponse(1L, "usuarioTest", "ADMIN");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(rolRepository.findByNombre(TipoRol.ADMIN)).thenReturn(Optional.of(rolAdmin));
        when(usuarioRepository.save(usuario)).thenReturn(usuarioActualizado);
        when(usuarioMapper.toResponse(usuarioActualizado)).thenReturn(responseEsperado);

        UsuarioResponse resultado = autenticacionService.cambiarRol(1L, request);

        assertEquals(1L, resultado.getId());
        assertEquals("usuarioTest", resultado.getUsername());
        assertEquals("ADMIN", resultado.getRol());

        verify(usuarioRepository).save(usuario);
    }
}