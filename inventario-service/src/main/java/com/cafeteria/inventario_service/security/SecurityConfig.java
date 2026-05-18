package com.cafeteria.inventario_service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth

                       
                        .requestMatchers(HttpMethod.GET, "/api/v1/inventarios/**")
                        .hasAnyRole("ADMIN", "EMPLEADO")

                    
                        .requestMatchers(HttpMethod.GET, "/api/v1/movimientos/**")
                        .hasAnyRole("ADMIN", "EMPLEADO")

                       
                        .requestMatchers(HttpMethod.POST, "/api/v1/inventarios/**")
                        .hasRole("ADMIN")

                        
                        .requestMatchers(HttpMethod.POST, "/api/v1/movimientos/**")
                        .hasRole("ADMIN")

                        
                        .requestMatchers(HttpMethod.PUT, "/api/v1/inventarios/**")
                        .hasRole("ADMIN")

                        
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/inventarios/**")
                        .hasRole("ADMIN")

                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )

                .build();
    }
}