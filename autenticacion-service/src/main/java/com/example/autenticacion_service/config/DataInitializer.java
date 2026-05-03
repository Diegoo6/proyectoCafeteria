package com.example.autenticacion_service.config;

import com.example.autenticacion_service.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.example.autenticacion_service.model.Rol;
import com.example.autenticacion_service.model.TipoRol;
import com.example.autenticacion_service.model.Usuario;
import com.example.autenticacion_service.repository.RolRepository;

@Configuration
public class DataInitializer {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;

    DataInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    @Transactional
    CommandLineRunner initData(RolRepository rolRepository) {
        return args -> 
        {
            if (rolRepository.findByNombre(TipoRol.ADMIN).isEmpty()) {
                rolRepository.save(new Rol(null, TipoRol.ADMIN));
            }

            if (rolRepository.findByNombre(TipoRol.EMPLEADO).isEmpty()) {
                rolRepository.save(new Rol(null, TipoRol.EMPLEADO));
                
            }

            if (usuarioRepository.findByUsername("admin").isEmpty()) {
                Rol rolAdmin = rolRepository.findByNombre(TipoRol.ADMIN).orElseThrow(() -> new RuntimeException("Rol no existe"));

                Usuario usuarioAdmin = new Usuario();
                usuarioAdmin.setUsername("admin");
                usuarioAdmin.setPassword(passwordEncoder.encode("admin"));
                usuarioAdmin.setRol(rolAdmin);

                usuarioRepository.save(usuarioAdmin);
            }
        };
    }
}
