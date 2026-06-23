package com.cafeteria.venta.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "empleado-service",
        url = "${empleado.service.url}"
)
public interface EmpleadoClient {

    @GetMapping("/api/v1/empleados/{id}") Object obtenerEmpleado(@PathVariable Long id);
}