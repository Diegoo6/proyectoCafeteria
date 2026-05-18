package com.cafeteria.reporte.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.cafeteria.reporte.dto.VentaResponse;

@FeignClient(name = "venta", url = "http://localhost:8085")

public interface VentaClient {

    @GetMapping("/api/v1/ventas")
    List<VentaResponse> listarVentas();

}