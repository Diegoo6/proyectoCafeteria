package com.cafeteria.reporte.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.cafeteria.reporte.dto.VentaResponse;

@FeignClient(name = "venta", url = "http://localhost:8081")

public interface VentaClient {

    @GetMapping("/api/ventas")
    List<VentaResponse> listarVentas();

}
