package com.cafeteria.reporte.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cafeteria.reporte.dto.ProductoResponse;

@FeignClient(name= "producto", url= "http://localhost:8083")

public interface ProductoClient {

    @GetMapping("/api/v1/productos/{id}")
    ProductoResponse buscarPorId(@PathVariable Long id);

}
