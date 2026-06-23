package com.cafeteria.inventario_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class FeignConfig {

    private final HttpServletRequest request;

    public FeignConfig(HttpServletRequest request) {
        this.request = request;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {

        return template -> {

            String authorization =
                    request.getHeader("Authorization");

            if (authorization != null) {

                template.header(
                        "Authorization",
                        authorization
                );
            }
        };
    }
}
