package com.cafeteria.reporte.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cafeteria.reporte.dto.DashboardResponse;
import com.cafeteria.reporte.dto.ProductoMasVendidoResponse;
import com.cafeteria.reporte.dto.TotalVentasResponse;
import com.cafeteria.reporte.dto.VentasPorEmpleadoResponse;
import com.cafeteria.reporte.dto.VentasPorFechaResponse;
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

    @GetMapping("/empleado/{empleadoId}")
    public ResponseEntity<VentasPorEmpleadoResponse> ventasPorEmpleado(@PathVariable Long empleadoId){

        return ResponseEntity.ok(reporteService.ventasPorEmpleado(empleadoId));
    }

    @GetMapping("/producto-mas-vendido")
    public ResponseEntity<ProductoMasVendidoResponse> productoMasVendido(){
        return ResponseEntity.ok(reporteService.productoMasVendido());
    }

    @GetMapping("/ventas-fecha")
    public ResponseEntity<VentasPorFechaResponse> ventasPorFecha(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha){

        return ResponseEntity.ok(reporteService.ventasPorFecha(fecha));
    }

    @GetMapping("/total-ventas")
    public ResponseEntity<TotalVentasResponse> totalVentas(){
        return ResponseEntity.ok(reporteService.totalVentas());
    }
}
