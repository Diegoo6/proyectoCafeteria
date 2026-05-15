package com.cafeteria.reporte.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafeteria.reporte.dto.DashboardResponse;
import com.cafeteria.reporte.service.ReporteService;

@RestController
@RequestMapping("/api/reportes")

public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService){
        this.reporteService = reporteService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponse> obtenerDashboard(){

        return ResponseEntity.ok(reporteService.obtenerDashboard());
    }
}
