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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimientoInventarioService {

    private static final Logger logger = LoggerFactory.getLogger(MovimientoInventarioService.class);

    private final MovimientoInventarioRepository movimientoRepository;

    private final InventarioRepository inventarioRepository;

    public List<MovimientoInventarioResponseDTO> listar() {

        logger.info("Listando todos los movimientos de inventario");

        return movimientoRepository.findAll()
                .stream()
                .map(MovimientoInventarioMapper::toResponse)
                .toList();
    }

    public MovimientoInventarioResponseDTO obtenerPorId(Long id) {

        logger.info("Buscando movimiento con id {}", id);

        MovimientoInventario movimiento =
                movimientoRepository.findById(id)
                        .orElseThrow(() -> {

                            logger.error("Movimiento {} no encontrado", id);

                            return new ResourceNotFoundException("Movimiento no encontrado");
                        });

        return MovimientoInventarioMapper.toResponse(movimiento);
    }

    public MovimientoInventarioResponseDTO guardar(
            MovimientoInventarioRequestDTO dto) {

        logger.info("Registrando movimiento tipo {} para inventario {}",
                dto.getTipo(),
                dto.getInventarioId());

        Inventario inventario = inventarioRepository.findById(dto.getInventarioId())
                        .orElseThrow(() -> {

                            logger.error("Inventario {} no encontrado",dto.getInventarioId());

                            return new ResourceNotFoundException("Inventario no encontrado");
                        });

        if (!dto.getTipo().equalsIgnoreCase("ENTRADA")
                && !dto.getTipo().equalsIgnoreCase("SALIDA")) {

            logger.warn("Tipo de movimiento inválido: {}",dto.getTipo());

            throw new BusinessException("El tipo debe ser ENTRADA o SALIDA");
        }

        if (dto.getTipo().equalsIgnoreCase("ENTRADA")) {

            logger.info("Agregando {} unidades al inventario {}",dto.getCantidad(),dto.getInventarioId());

            inventario.setStock(inventario.getStock() + dto.getCantidad()
            );
        }

        if (dto.getTipo().equalsIgnoreCase("SALIDA")) {

            if (inventario.getStock() < dto.getCantidad()) {

                logger.warn("Stock insuficiente para inventario {}",dto.getInventarioId());

                throw new BusinessException("Stock insuficiente"
                );
            }

            logger.info("Descontando {} unidades del inventario {}",dto.getCantidad(),dto.getInventarioId());

            inventario.setStock(inventario.getStock() - dto.getCantidad());
        }

        inventario.setDisponible(inventario.getStock() > 0);

        inventarioRepository.save(inventario);

        MovimientoInventario movimiento = MovimientoInventarioMapper.toEntity(dto,inventario);

        MovimientoInventario guardado = movimientoRepository.save(movimiento);

        logger.info("Movimiento {} registrado correctamente",guardado.getId());

        return MovimientoInventarioMapper.toResponse(guardado);
    }

    public void eliminar(Long id) {

        logger.info("Eliminando movimiento {}", id);

        if (!movimientoRepository.existsById(id)) {

            logger.error("Movimiento {} no encontrado", id);

            throw new ResourceNotFoundException("Movimiento no encontrado");
        }

        movimientoRepository.deleteById(id);

        logger.info("Movimiento {} eliminado correctamente", id);
    }

    public List<MovimientoInventarioResponseDTO> listarPorInventario(Long inventarioId) {

        logger.info("Listando movimientos para inventario {}",inventarioId);

        return movimientoRepository
                .findByInventarioId(inventarioId)
                .stream()
                .map(MovimientoInventarioMapper::toResponse)
                .toList();
    }
}