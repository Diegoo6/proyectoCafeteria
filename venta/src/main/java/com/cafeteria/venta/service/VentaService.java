package com.cafeteria.venta.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cafeteria.venta.model.Venta;
import com.cafeteria.venta.repository.VentaRepository;

@Service

public class VentaService {

    private final VentaRepository ventaRepository;

    public VentaService (VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    public List <Venta> listarVentas(){                 //Devolver una lista con todas las ventas
        return ventaRepository.findAll();    
    }

    public Venta encontrarPorId (Long id){            //Buscar una venta por su Id
        return ventaRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
    }

    public Venta guardarVenta (Venta venta){          //Para guardar una nueva venta
        return ventaRepository.save(venta);
    }

    public void eliminarPorId (Long id){            //Para eliminar una venta por su id
        ventaRepository.deleteById(id);
    }

}
