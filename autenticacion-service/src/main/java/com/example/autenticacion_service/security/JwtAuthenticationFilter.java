package com.example.autenticacion_service.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UsuarioDetailsService usuarioDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UsuarioDetailsService usuarioDetailsService) {
        this.jwtService = jwtService;
        this.usuarioDetailsService = usuarioDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        final String authHeader = request.getHeader("Authorization");
        // El HttpRequest llega en forma de clave:valor, donde cada par de clave:valor es un Header
        // Busca el header "Authorization" y guarda su valor en authHeader. La clave es el parametro de busqueda.

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
            // Si el header es null o no empieza con "Bearer ", no trae token y el request continúa sin ser autenticado
        }

        final String token = authHeader.substring(7);
        // Se elimina "Bearer " (7 primeros caracteres) del autHeader para quedarse solo con el texto del token

        final String username = jwtService.extractUsername(token);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Verifica que el username no esté autenticado

            UserDetails userDetails = usuarioDetailsService.loadUserByUsername(username);
            // Carga usuario desde la base de datos

            if (jwtService.isTokenValid(token, userDetails.getUsername())) {
                // Si el token es valido, se autentica el usuario

                UsernamePasswordAuthenticationToken autenticado = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(autenticado);
                
            }
            
        }

        filterChain.doFilter(request, response);
    }
    
}
