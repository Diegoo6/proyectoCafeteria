package producto_service.security;

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

                        
                        .requestMatchers(HttpMethod.GET, "/productos/**")
                        .hasAnyRole("ADMIN", "EMPLEADO")

                        .requestMatchers(HttpMethod.GET, "/categorias/**")
                        .hasAnyRole("ADMIN", "EMPLEADO")

                        
                        .requestMatchers(HttpMethod.POST, "/productos/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/categorias/**")
                        .hasRole("ADMIN")

                        
                        .requestMatchers(HttpMethod.PUT, "/productos/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/categorias/**")
                        .hasRole("ADMIN")

                        
                        .requestMatchers(HttpMethod.DELETE, "/productos/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/categorias/**")
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