package com.cafeteria.reporte.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cafeteria.reporte.client.EmpleadoClient;
import com.cafeteria.reporte.client.ProductoClient;
import com.cafeteria.reporte.client.VentaClient;
import com.cafeteria.reporte.dto.DashboardResponse;
import com.cafeteria.reporte.dto.EmpleadoResponse;
import com.cafeteria.reporte.dto.ProductoMasVendidoResponse;
import com.cafeteria.reporte.dto.ProductoResponse;
import com.cafeteria.reporte.dto.TotalVentasResponse;
import com.cafeteria.reporte.dto.VentaResponse;
import com.cafeteria.reporte.dto.VentasPorEmpleadoResponse;
import com.cafeteria.reporte.dto.VentasPorFechaResponse;

@Service
public class ReporteService {

    private final VentaClient ventaClient;
    private final EmpleadoClient empleadoClient;
    private final ProductoClient productoClient;

    public ReporteService(VentaClient ventaClient,
                          EmpleadoClient empleadoClient,
                          ProductoClient productoClient) {
        this.ventaClient = ventaClient;
        this.empleadoClient = empleadoClient;
        this.productoClient = productoClient;
    }

    public DashboardResponse obtenerDashboard() {

        List<VentaResponse> ventas = ventaClient.listarVentas();

        DashboardResponse dashboard = new DashboardResponse();

        int ventasHoy = 0;
        double totalHoy = 0;

        for (VentaResponse venta : ventas) {

            if (venta.getFechaVenta().equals(LocalDate.now())) {
                ventasHoy++;
                totalHoy += venta.getTotal();
            }

        }

        dashboard.setVentasHoy(ventasHoy);
        dashboard.setTotalHoy(totalHoy);
        dashboard.setProductoMasVendido("Proximamente");

        return dashboard;
    }

    public VentasPorEmpleadoResponse ventasPorEmpleado(Long empleadoId) {

        List<VentaResponse> ventas = ventaClient.listarVentas();

        EmpleadoResponse empleado = empleadoClient.buscarPorId(empleadoId);

        int cantidadVentas = 0;
        double totalVendido = 0;

        for (VentaResponse venta : ventas) {

            if (venta.getEmpleadoId().equals(empleadoId)) {
                cantidadVentas++;
                totalVendido += venta.getTotal();
            }
        }

        VentasPorEmpleadoResponse response = new VentasPorEmpleadoResponse();

        response.setEmpleado(empleado.getNombre() + " " + empleado.getApellido());
        response.setCantidadVentas(cantidadVentas);
        response.setTotalVendido(totalVendido);

        return response;
    }

    public ProductoMasVendidoResponse productoMasVendido() {

        List<VentaResponse> ventas = ventaClient.listarVentas();

        Map<Long, Integer> contadorProductos = new HashMap<>();

        for (VentaResponse venta : ventas) {

            if (venta.getDetalles() != null) {

                venta.getDetalles().forEach(detalle -> {
                    Long productoId = detalle.getProductoId();
                    Integer cantidad = detalle.getCantidad();

                    contadorProductos.put(
                            productoId,
                            contadorProductos.getOrDefault(productoId, 0) + cantidad
                    );
                });
            }
        }

        Long productoMasVendidoId = contadorProductos.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        ProductoMasVendidoResponse response = new ProductoMasVendidoResponse();

        if (productoMasVendidoId == null) {
            response.setNombreProducto("Sin ventas registradas");
            response.setCantidadVendida(0);
            response.setCategoria("Sin categoria");
            return response;
        }

        ProductoResponse producto = productoClient.buscarPorId(productoMasVendidoId);

        response.setNombreProducto(producto.getNombre());
        response.setCantidadVendida(contadorProductos.get(productoMasVendidoId));
        response.setCategoria(producto.getCategoriaNombre());

        return response;
    }

    public VentasPorFechaResponse ventasPorFecha(LocalDate fecha){

        List<VentaResponse> ventas = ventaClient.listarVentas();

        int cantidadVentas = 0;
        double totalVendido = 0;

        for (VentaResponse venta : ventas){

            if (venta.getFechaVenta().equals(fecha)){

                cantidadVentas++;

                totalVendido += venta.getTotal();
            }
        }

        VentasPorFechaResponse response = new VentasPorFechaResponse();

        response.setFecha(fecha);
        response.setCantidadVentas(cantidadVentas);
        response.setTotalVendido(totalVendido);

        return response;


    }

    public TotalVentasResponse totalVentas() {

        List<VentaResponse> ventas = ventaClient.listarVentas();

        double total = 0;

        for (VentaResponse venta : ventas){
            total += venta.getTotal();
        }

        TotalVentasResponse response = new TotalVentasResponse();

        response.setTotalVentas(total);

        return response;
    }

}