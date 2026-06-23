package com.cafeteria.venta.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cafeteria.venta.dto.ProductoResponse;

@FeignClient(
        name = "producto-service",
        url = "${producto.service.url}"
)
public interface ProductoClient {

    @GetMapping("/api/v1/productos/{id}")
    ProductoResponse obtenerProducto(
            @PathVariable Long id
    );
}