package com.cafeteria.venta.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

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

    public VentaService(VentaRepository ventaRepository, VentaMapper ventaMapper){
        this.ventaRepository = ventaRepository;
        this.ventaMapper = ventaMapper;
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
        
        Venta venta = ventaMapper.toEntity(request);

        double total = 0;

        for(DetalleVenta detalle : venta.getDetalleVenta()){

            double precio = 5000; //esto es momentaneo, solo para probar, despues tendre que llamar al microservicio producto
            
            detalle.setPrecioUnitario(precio);
            detalle.setSubtotal(precio * detalle.getCantidad());

            detalle.setVenta(venta);

            total += detalle.getSubtotal();

        }

        venta.setTotal(total);

        venta.setFechaVenta(LocalDate.now());


        Venta ventaGuardada = ventaRepository.save(venta);

        return ventaMapper.toResponse(ventaGuardada);
    }

    public void eliminarPorId(Long id){
        ventaRepository.deleteById(id);
    }

}
