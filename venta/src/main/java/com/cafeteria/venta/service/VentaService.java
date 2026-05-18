package com.cafeteria.venta.service;


import java.time.LocalDate;
import java.util.List;

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

    public List<VentaResponse> listarVentas(){   //Listar todas las ventas
        return ventaRepository.findAll()
            .stream()
            .map(ventaMapper::toResponse)
            .toList();
    }

    public VentaResponse buscarPorId(Long id){   //Para buscar venta por id
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Venta no encontrada"));

        return ventaMapper.toResponse(venta);        
    }

    public VentaResponse guardarVenta(VentaRequest request){

        empleadoClient.obtenerEmpleado(request.getEmpleadoId());
        
        Venta venta = ventaMapper.toEntity(request);

        venta.setFechaVenta(LocalDate.now());

        double total = 0;

        for(DetalleVenta detalle : venta.getDetalleVenta()){

            ProductoResponse producto = productoClient.obtenerProducto(detalle.getProductoId());
            

            double precio = producto.getPrecio();
            
            detalle.setPrecioUnitario(precio);
            detalle.setSubtotal(precio * detalle.getCantidad());
            detalle.setVenta(venta);

            inventarioClient.descontarStock(
            detalle.getProductoId(),
            detalle.getCantidad()
            );


            total += detalle.getSubtotal();

        }

        venta.setTotal(total);


        Venta ventaGuardada = ventaRepository.save(venta);

        return ventaMapper.toResponse(ventaGuardada);
    }

    public void eliminarPorId(Long id){
        ventaRepository.deleteById(id);
    }



}
