package com.cafeteria.inventario_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "producto-service",
        url = "${producto.service.url}"
)
public interface ProductoClient {

    @GetMapping("/api/v1/productos/{id}")
    Object obtenerProducto(
            @PathVariable Long id
    );
}