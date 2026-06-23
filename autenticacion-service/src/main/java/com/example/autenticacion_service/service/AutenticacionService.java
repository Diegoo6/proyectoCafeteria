package com.example.autenticacion_service.service;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AutenticacionService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final UsuarioMapper usuarioMapper;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AutenticacionService(UsuarioRepository usuarioRepository, RolRepository rolRepository, UsuarioMapper usuarioMapper, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.usuarioMapper = usuarioMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public UsuarioResponse registrarUsuario(RegisterRequest request) {
        if (usuarioRepository.findByUsername(request.getUsername()).isPresent()) {  // Validar que el usuario no exista
            log.warn("El usuario {} ya existe.", request.getUsername());
            throw new RuntimeException("El usuario ya existe");
        }

        Usuario usuarioNuevo = usuarioMapper.toEntity(request);
        Rol rolOtorgado = rolRepository.findByNombre(TipoRol.EMPLEADO).orElseThrow(()
                        -> {log.error("Rol no configurado en la base de datos");
                        return new RuntimeException("Error interno: Rol EMPLEADO no configurado en la base de datos");}); // Guarda el rol que se quiere asignar

        usuarioNuevo.setPassword(passwordEncoder.encode(usuarioNuevo.getPassword()));
        usuarioNuevo.setRol(rolOtorgado);

        Usuario usuarioGuardado = usuarioRepository.save(usuarioNuevo);

        log.info("Usuario {} agregado correctamente.", request.getUsername());
        return usuarioMapper.toResponse(usuarioGuardado);

    }

    public LoginResponse usuarioLogin(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        Usuario usuarioLogin = usuarioRepository.findByUsername(request.getUsername()).orElseThrow(() ->
                            {log.warn("Usuario {} no encontrado.", request.getUsername());
                            return new RuntimeException("Usuario no encontrado");});

        String token = jwtService.generateToken(usuarioLogin.getUsername(), usuarioLogin.getRol().getNombre().name());

        log.info("Login exitoso.");
        return new LoginResponse(token);
    }

    @Transactional(readOnly = true)
    public UsuarioResponse buscarPorId(Long id) {
        Usuario usuarioEncontrado = usuarioRepository.findById(id).orElseThrow(()
                                    -> {log.warn("Usuario con id {} no encontrado.", id);
                                    return new RuntimeException("Usuario no encontrado");});

        log.info("Usuario con id {} encontrado.", id);
        return usuarioMapper.toResponse(usuarioEncontrado);
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponse> listarUsuarios() {
        List<Usuario> listaUsuarios = usuarioRepository.findAll();

        log.info("Listando usuarios. {} encontrados", listaUsuarios.size());
        return listaUsuarios.stream()
                            .map(usuarioMapper::toResponse)
                            .toList();
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponse> listarPorRol(TipoRol rol) {
        List<Usuario> listaRol = usuarioRepository.findByRolNombre(rol);

        log.info("Listando usuarios con rol {}. {} encontrados.", rol, listaRol.size());
        return listaRol.stream()
                .map(usuarioMapper::toResponse)
                .toList();
    }

    @Transactional
    public UsuarioResponse cambiarRol(Long id, NuevoRolRequest request) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() ->
                        {log.warn("Usuario con id {} no encontrado.", id);
                        return new RuntimeException("Usuario no encontrado");});

        Rol nuevoRol = rolRepository.findByNombre(request.getNuevoRol()).orElseThrow(() ->
                    {log.error("Rol {} no configurado en la base de datos.", request.getNuevoRol());
                    return new RuntimeException("Rol inválido");});

        usuario.setRol(nuevoRol);

        Usuario usuarioActualizado = usuarioRepository.save(usuario);

        log.info("Rol de usuario con id {} modificado exitosamente.", id);
        return usuarioMapper.toResponse(usuarioActualizado);
    }

    @Transactional
    public void eliminarPorId(Long id) {
        Usuario aEliminar = usuarioRepository.findById(id).orElseThrow(() ->
                            {log.warn("Usuario con id {} no encontrado.", id);
                            return new RuntimeException("Usuario no encontrado");});

        log.info("Usuario {} eliminado exitosamente.", id);
        usuarioRepository.delete(aEliminar);
    }
    
}
