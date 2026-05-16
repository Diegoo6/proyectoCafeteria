package com.cafeteria.reporte.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cafeteria.reporte.dto.EmpleadoResponse;

@FeignClient(name = "empleado", url= "http://localhost:8082")

public interface EmpleadoClient {

    @GetMapping("/api/v1/empleados/{id}")
    EmpleadoResponse buscarPorId(@PathVariable Long id);



}
