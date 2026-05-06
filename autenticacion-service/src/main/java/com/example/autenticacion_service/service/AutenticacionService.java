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
import com.example.autenticacion_service.dto.TokenValidationResponse;
import com.example.autenticacion_service.dto.UsuarioResponse;
import com.example.autenticacion_service.mapper.UsuarioMapper;
import com.example.autenticacion_service.model.Rol;
import com.example.autenticacion_service.model.TipoRol;
import com.example.autenticacion_service.model.Usuario;
import com.example.autenticacion_service.repository.RolRepository;
import com.example.autenticacion_service.repository.UsuarioRepository;
import com.example.autenticacion_service.security.JwtService;

@Service
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
            throw new RuntimeException("El usuario ya existe");
        }

        Usuario usuarioNuevo = usuarioMapper.toEntity(request);
        Rol rolOtorgado = rolRepository.findByNombre(TipoRol.EMPLEADO).orElseThrow(() -> new RuntimeException("Rol no encontrado")); // Guarda el rol que se quiere asignar

        usuarioNuevo.setPassword(passwordEncoder.encode(usuarioNuevo.getPassword()));
        usuarioNuevo.setRol(rolOtorgado);

        Usuario usuarioGuardado = usuarioRepository.save(usuarioNuevo);

        return usuarioMapper.toResponse(usuarioGuardado);

    }

    public LoginResponse usuarioLogin(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        Usuario usuarioLogin = usuarioRepository.findByUsername(request.getUsername()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String token = jwtService.generateToken(usuarioLogin.getUsername());

        return new LoginResponse(token);
    }

    @Transactional (readOnly = true)
    public UsuarioResponse buscarPorId(Long id) {
        Usuario usuarioEncontrado = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return usuarioMapper.toResponse(usuarioEncontrado);
    }

    @Transactional (readOnly = true)
    public List<UsuarioResponse> listarUsuarios() {
        List<Usuario> listaUsuarios = usuarioRepository.findAll();

        return listaUsuarios.stream()
                            .map(usuarioMapper::toResponse)
                            .toList();
    }

    @Transactional (readOnly = true)
    public List<UsuarioResponse> listarPorRol(TipoRol rol) {
        List<Usuario> listaRol = usuarioRepository.findByRolNombre(rol);

        return listaRol.stream()
                .map(usuarioMapper::toResponse)
                .toList();
    }

    @Transactional
    public UsuarioResponse cambiarRol(Long id, NuevoRolRequest request) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Rol nuevoRol = rolRepository.findByNombre(request.getNuevoRol()).orElseThrow(() -> new RuntimeException("Rol inválido"));

        usuario.setRol(nuevoRol);

        Usuario usuarioActualizado = usuarioRepository.save(usuario);

        return usuarioMapper.toResponse(usuarioActualizado);
    }

    @Transactional
    public void eliminarPorId(Long id) {
        Usuario aEliminar = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuarioRepository.delete(aEliminar);
    }

    public TokenValidationResponse validarToken(String autHeader) {
        if (autHeader == null || !autHeader.startsWith("Bearer ")) {
            return new TokenValidationResponse(false, null, null);
        }

        String token = autHeader.substring(7);
        String username = jwtService.extractUsername(token);

        Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("El usuario no existe"));

        boolean tokenValido = jwtService.isTokenValid(token, usuario.getUsername());

        if (!tokenValido) {
            return new TokenValidationResponse(false, null, null);
        }

        return new TokenValidationResponse(true, usuario.getUsername(), usuario.getRol().getNombre());
    }
    
}
