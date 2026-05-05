package com.cafeteria.venta.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.cafeteria.venta.dto.DetalleVentaRequest;
import com.cafeteria.venta.dto.DetalleVentaResponse;
import com.cafeteria.venta.dto.VentaRequest;
import com.cafeteria.venta.dto.VentaResponse;
import com.cafeteria.venta.model.DetalleVenta;
import com.cafeteria.venta.model.Venta;

@Component
public class VentaMapper {

    public VentaResponse toResponse(Venta venta){
        if (venta == null)
            return null;

        VentaResponse respuesta = new VentaResponse();

        respuesta.setId(venta.getId());
        respuesta.setFechaVenta(venta.getFechaVenta());
        respuesta.setTotal(venta.getTotal());
        respuesta.setEstadoDePago(venta.getEstadoDePago());
        respuesta.setMetodoDePago(venta.getMetodoDePago());
        respuesta.setObservacion(venta.getObservacion());
        respuesta.setEmpleadoId(venta.getEmpleadoId());

        if (venta.getDetalleVenta() != null){
            List<DetalleVentaResponse> detalles = venta.getDetalleVenta()
                .stream()
                .map(this::toDetalleResponse)
                .collect(Collectors.toList());
            respuesta.setDetalles(detalles);
        }

        return respuesta;

        
    }

    public DetalleVentaResponse toDetalleResponse(DetalleVenta detalle){
        DetalleVentaResponse respuesta = new DetalleVentaResponse();
        
        respuesta.setProductoId(detalle.getProductoId());
        respuesta.setCantidad(detalle.getCantidad());
        respuesta.setPrecioUnitario(detalle.getPrecioUnitario());
        respuesta.setSubtotal(detalle.getSubtotal());

        return respuesta;
    }

    public Venta toEntity(VentaRequest request){
        if (request == null) return null;

        Venta venta = new Venta();

        venta.setEstadoDePago(request.getEstadoDePago());
        venta.setMetodoDePago(request.getMetodoDePago());
        venta.setObservacion(request.getObservacion());
        venta.setEmpleadoId(request.getEmpleadoId());

        if (request.getDetalles() != null){
            List<DetalleVenta> detalles = request.getDetalles()
                .stream()
                .map(this::toDetalleEntity)
                .collect(Collectors.toList());

            detalles.forEach(d -> d.setVenta(venta));  //cada elemento "d" es un DetalleVenta

            venta.setDetalleVenta(detalles);
        }
        
        return venta;
    }

    public DetalleVenta toDetalleEntity(DetalleVentaRequest request){
        if (request == null)
            return null;

        DetalleVenta detalle = new DetalleVenta();

        detalle.setProductoId(request.getProductoId());
        detalle.setCantidad(request.getCantidad());

        return detalle;
    }

}
