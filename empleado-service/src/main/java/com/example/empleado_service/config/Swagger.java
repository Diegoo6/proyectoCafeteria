package com.example.empleado_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT"
)
public class Swagger {
    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI().info(new Info()
                                  .title("API Empleado")
                                  .version("1.0")
                                  .description("Microservicio de Empleado"));
    }
    
}
