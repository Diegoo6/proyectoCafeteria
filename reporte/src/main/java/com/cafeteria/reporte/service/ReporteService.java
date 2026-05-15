package com.cafeteria.reporte.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cafeteria.reporte.dto.DashboardResponse;
import com.cafeteria.reporte.dto.VentaResponse;
import com.cafeteria.reporte.feign.VentaClient;

@Service

public class ReporteService {

    private final VentaClient ventaClient;

    public ReporteService(VentaClient ventaClient){
        this.ventaClient = ventaClient;
    }

    public DashboardResponse obtenerDashboard(){

        List<VentaResponse> ventas = ventaClient.listarVentas();

        DashboardResponse dashboard = new DashboardResponse();

        int ventasHoy = 0;
        double totalHoy = 0;
        int ventasPendientes = 0;

        for (VentaResponse venta : ventas){

            if(venta.getFechaVenta().equals(LocalDate.now())){
                ventasHoy++;
                totalHoy += venta.getTotal();
            }

            if(venta.getEstadoDePago().equalsIgnoreCase("pendiente")){
                ventasPendientes++;
            }
        }

        dashboard.setVentasHoy(ventasHoy);
        dashboard.setTotalHoy(totalHoy);
        dashboard.setVentasPendientes(ventasPendientes);

        dashboard.setProductoMasVendido("Proximamente");

        return dashboard;
    }



}
