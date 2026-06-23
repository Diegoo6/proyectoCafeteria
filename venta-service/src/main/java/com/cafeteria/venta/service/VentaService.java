package com.cafeteria.venta.service;


import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cafeteria.venta.client.EmpleadoClient;
import com.cafeteria.venta.client.InventarioClient;
import com.cafeteria.venta.client.ProductoClient;
import com.cafeteria.venta.dto.ProductoResponse;
import com.cafeteria.venta.dto.VentaRequest;
import com.cafeteria.venta.dto.VentaResponse;
import com.cafeteria.venta.mapper.VentaMapper;
import com.cafeteria.venta.model.DetalleVenta;
import com.cafeteria.venta.model.Venta;
import com.cafeteria.venta.repository.VentaRepository;

@Service
public class VentaService {

    private static final Logger logger = LoggerFactory.getLogger(VentaService.class);

    private final VentaRepository ventaRepository;
    private final VentaMapper ventaMapper;
    private final ProductoClient productoClient;
    private final InventarioClient inventarioClient;
    private final EmpleadoClient empleadoClient;

    public VentaService(VentaRepository ventaRepository, VentaMapper ventaMapper, ProductoClient productoClient, InventarioClient inventarioClient, EmpleadoClient empleadoClient){
        this.ventaRepository = ventaRepository;
        this.ventaMapper = ventaMapper;
        this.productoClient = productoClient;
        this.inventarioClient = inventarioClient;
        this.empleadoClient = empleadoClient;
    }

    public List<VentaResponse> listarVentas(){
        logger.info("Listando todas las ventas");

        List<VentaResponse> ventas = ventaRepository.findAll()
                .stream()
                .map(ventaMapper::toResponse)
                .toList();

        logger.info("Se encontraron {} ventas", ventas.size());

        return ventas;
    }

    public VentaResponse buscarPorId(Long id){
        logger.info("Buscando venta con id {}", id);

        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Venta con id {} no encontrada", id);
                    return new RuntimeException("Venta no encontrada");
                });

        logger.info("Venta con id {} encontrada correctamente", id);

        return ventaMapper.toResponse(venta);
    }

    public VentaResponse guardarVenta(VentaRequest request){
        logger.info("Iniciando creación de venta para empleado {}", request.getEmpleadoId());

        empleadoClient.obtenerEmpleado(request.getEmpleadoId());
        logger.info("Empleado {} validado correctamente", request.getEmpleadoId());

        Venta venta = ventaMapper.toEntity(request);
        venta.setFechaVenta(LocalDate.now());

        double total = 0;

        for (DetalleVenta detalle : venta.getDetalleVenta()) {
            logger.info("Procesando producto {} con cantidad {}", detalle.getProductoId(), detalle.getCantidad());

            ProductoResponse producto = productoClient.obtenerProducto(detalle.getProductoId());
            logger.info("Producto {} obtenido correctamente con precio {}", detalle.getProductoId(), producto.getPrecio());

            double precio = producto.getPrecio();

            detalle.setPrecioUnitario(precio);
            detalle.setSubtotal(precio * detalle.getCantidad());
            detalle.setVenta(venta);

            inventarioClient.descontarStock(
                    detalle.getProductoId(),
                    detalle.getCantidad()
            );

            logger.info("Stock descontado para producto {} en cantidad {}", detalle.getProductoId(), detalle.getCantidad());

            total += detalle.getSubtotal();
        }

        venta.setTotal(total);
        logger.info("Total calculado para la venta: {}", total);

        Venta ventaGuardada = ventaRepository.save(venta);

        logger.info("Venta guardada correctamente con id {}", ventaGuardada.getId());

        return ventaMapper.toResponse(ventaGuardada);
    }

    public void eliminarPorId(Long id){
        logger.info("Intentando eliminar venta con id {}", id);

        if (!ventaRepository.existsById(id)) {
            logger.warn("No se pudo eliminar. Venta con id {} no encontrada", id);
            throw new RuntimeException("Venta no encontrada");
        }

        ventaRepository.deleteById(id);

        logger.info("Venta con id {} eliminada correctamente", id);
    }
}