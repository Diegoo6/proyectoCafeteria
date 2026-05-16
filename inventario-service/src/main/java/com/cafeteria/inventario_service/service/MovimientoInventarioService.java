package com.cafeteria.inventario_service.service;

import com.cafeteria.inventario_service.dto.MovimientoInventarioRequestDTO;
import com.cafeteria.inventario_service.dto.MovimientoInventarioResponseDTO;
import com.cafeteria.inventario_service.exception.BusinessException;
import com.cafeteria.inventario_service.exception.ResourceNotFoundException;
import com.cafeteria.inventario_service.mapper.MovimientoInventarioMapper;
import com.cafeteria.inventario_service.model.Inventario;
import com.cafeteria.inventario_service.model.MovimientoInventario;
import com.cafeteria.inventario_service.repository.InventarioRepository;
import com.cafeteria.inventario_service.repository.MovimientoInventarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimientoInventarioService {

    private final MovimientoInventarioRepository movimientoRepository;
    private final InventarioRepository inventarioRepository;

    
    public List<MovimientoInventarioResponseDTO> listar() {

        return movimientoRepository.findAll()
                .stream()
                .map(MovimientoInventarioMapper::toResponse)
                .toList();
    }

   
    public MovimientoInventarioResponseDTO obtenerPorId(Long id) {

        MovimientoInventario movimiento =
                movimientoRepository.findById(id)
                        .orElseThrow(() ->new ResourceNotFoundException("Movimiento no encontrado"));

        return MovimientoInventarioMapper.toResponse(movimiento);
    }

    
    public MovimientoInventarioResponseDTO guardar(MovimientoInventarioRequestDTO dto) {

        
        Inventario inventario =
                inventarioRepository.findById(dto.getInventarioId())
                        .orElseThrow(() ->new ResourceNotFoundException("Inventario no encontrado"));

        
        if (!dto.getTipo().equalsIgnoreCase("ENTRADA")
                && !dto.getTipo().equalsIgnoreCase("SALIDA")) {

            throw new BusinessException("El tipo debe ser ENTRADA o SALIDA");
        }

      
        if (dto.getTipo().equalsIgnoreCase("ENTRADA")) {

            inventario.setStock(
                    inventario.getStock() + dto.getCantidad()
            );
        }

       
        if (dto.getTipo().equalsIgnoreCase("SALIDA")) {

           
            if (inventario.getStock() < dto.getCantidad()) {

                throw new BusinessException(
                        "Stock insuficiente"
                );
            }

            inventario.setStock(
                    inventario.getStock() - dto.getCantidad()
            );
        }

        
        inventario.setDisponible(
                inventario.getStock() > 0
        );

        
        inventarioRepository.save(inventario);

       
        MovimientoInventario movimiento =
                MovimientoInventarioMapper.toEntity(
                        dto,
                        inventario
                );

        MovimientoInventario guardado =
                movimientoRepository.save(movimiento);

        return MovimientoInventarioMapper.toResponse(guardado);
    }

    
    public void eliminar(Long id) {

        if (!movimientoRepository.existsById(id)) {

            throw new ResourceNotFoundException(
                    "Movimiento no encontrado"
            );
        }

        movimientoRepository.deleteById(id);
    }

    
    public List<MovimientoInventarioResponseDTO>
    listarPorInventario(Long inventarioId) {

        return movimientoRepository
                .findByInventarioId(inventarioId)
                .stream()
                .map(MovimientoInventarioMapper::toResponse)
                .toList();
    }
}