package com.cafeteria.inventario_service.service;

import com.cafeteria.inventario_service.client.ProductoClient;
import com.cafeteria.inventario_service.dto.InventarioRequestDTO;
import com.cafeteria.inventario_service.dto.InventarioResponseDTO;
import com.cafeteria.inventario_service.exception.BusinessException;
import com.cafeteria.inventario_service.exception.ResourceNotFoundException;
import com.cafeteria.inventario_service.mapper.InventarioMapper;
import com.cafeteria.inventario_service.model.Inventario;
import com.cafeteria.inventario_service.model.MovimientoInventario;
import com.cafeteria.inventario_service.repository.InventarioRepository;
import com.cafeteria.inventario_service.repository.MovimientoInventarioRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventarioService {

    private static final Logger logger = LoggerFactory.getLogger(InventarioService.class);

    private final InventarioRepository inventarioRepository;

    private final MovimientoInventarioRepository movimientoRepository;

    private final ProductoClient productoClient;

    public List<InventarioResponseDTO> listar() {

        logger.info("Listando todos los inventarios");

        return inventarioRepository.findAll()
                .stream()
                .map(InventarioMapper::toResponse)
                .toList();
    }

    public InventarioResponseDTO obtenerPorId(Long id) {

        logger.info("Buscando inventario con id {}", id);

        Inventario inventario = inventarioRepository.findById(id)
                .orElseThrow(() -> {

                    logger.error("Inventario {} no encontrado", id);

                    return new ResourceNotFoundException("Inventario no encontrado");
                });

        return InventarioMapper.toResponse(inventario);
    }

    public InventarioResponseDTO guardar(InventarioRequestDTO dto) {

        logger.info("Intentando crear inventario para producto {}",dto.getProductoId());

        try {

            productoClient.obtenerProducto(dto.getProductoId());

            logger.info("Producto {} validado correctamente",dto.getProductoId());

        } catch (Exception e) {

            logger.error("Producto {} no existe",dto.getProductoId());

            throw new BusinessException("El producto no existe");
        }

        if (inventarioRepository.existsByProductoId(dto.getProductoId())) {

            logger.warn("Ya existe inventario para producto {}",dto.getProductoId());

            throw new BusinessException("Ya existe inventario para este producto");
        }

        Inventario inventario = InventarioMapper.toEntity(dto);

        inventario.setDisponible(inventario.getStock() > 0);

        Inventario guardado = inventarioRepository.save(inventario);

        logger.info("Inventario creado correctamente con id {}",
                guardado.getId());

        MovimientoInventario movimiento = new MovimientoInventario();

        movimiento.setInventario(guardado);

        movimiento.setTipo("ENTRADA");

        movimiento.setCantidad(guardado.getStock());

        movimiento.setMotivo("Stock inicial");

        movimientoRepository.save(movimiento);

        logger.info("Movimiento inicial registrado para inventario {}",
                guardado.getId());

        logger.info("Inventario creado correctamente con id {}",
                guardado.getId());

        return InventarioMapper.toResponse(guardado);
    }

    @Transactional
    public InventarioResponseDTO actualizar(Long id,InventarioRequestDTO dto) {


        logger.info("Actualizando inventario con id {}", id);

        Inventario inventario = inventarioRepository.findById(id)
                .orElseThrow(() -> {

                    logger.error("Inventario {} no encontrado", id);

                    return new ResourceNotFoundException("Inventario no encontrado");
                });

        try {

            productoClient.obtenerProducto(dto.getProductoId());

            logger.info("Producto {} validado correctamente", dto.getProductoId());

        } catch (Exception e) {

            logger.error("Producto {} no existe", dto.getProductoId());

            throw new BusinessException("El producto no existe");
        }

        Integer stockAnterior = inventario.getStock();

        inventario.setProductoId(dto.getProductoId());

        inventario.setStock(dto.getStock());

        inventario.setDisponible(dto.getStock() > 0);

        inventario.setFechaActualizacion(LocalDateTime.now());

        Integer diferencia = dto.getStock() - stockAnterior;

        if (diferencia > 0) {

            MovimientoInventario movimiento = new MovimientoInventario();

            movimiento.setInventario(inventario);
            movimiento.setTipo("ENTRADA");
            movimiento.setCantidad(diferencia);
            movimiento.setMotivo("Ajuste manual de inventario");

            movimientoRepository.save(movimiento);

            logger.info("Movimiento ENTRADA registrado por {} unidades", diferencia);
        }

        if (diferencia < 0) {

            MovimientoInventario movimiento = new MovimientoInventario();

            movimiento.setInventario(inventario);
            movimiento.setTipo("SALIDA");
            movimiento.setCantidad(Math.abs(diferencia));
            movimiento.setMotivo("Ajuste manual de inventario");

            movimientoRepository.save(movimiento);

            logger.info("Movimiento SALIDA registrado por {} unidades",
                    Math.abs(diferencia));
        }

        Inventario actualizado = inventarioRepository.save(inventario);

        logger.info("Inventario {} actualizado correctamente", id);

        return InventarioMapper.toResponse(actualizado);

        }

    public void eliminar(Long id) {

        logger.info("Eliminando inventario con id {}", id);

        if (!inventarioRepository.existsById(id)) {

            logger.error("Inventario {} no encontrado", id);

            throw new ResourceNotFoundException("Inventario no encontrado");
        }

        inventarioRepository.deleteById(id);

        logger.info("Inventario {} eliminado correctamente", id);
    }

    public InventarioResponseDTO buscarPorProductoId(Long productoId) {

        logger.info("Buscando inventario para producto {}",productoId);

        Inventario inventario = inventarioRepository
                .findByProductoId(productoId)
                .orElseThrow(() -> {

                    logger.error("Inventario no encontrado para producto {}",productoId);

                    return new ResourceNotFoundException("Inventario no encontrado");
                });

        return InventarioMapper.toResponse(inventario);
    }

    @Transactional
    public void descontarStock(Long productoId, Integer cantidad) {

        logger.info("Iniciando descuento de {} unidades para producto {}",cantidad, productoId);

        Inventario inventario = inventarioRepository
                .findByProductoId(productoId)
                .orElseThrow(() -> {

                    logger.error("Inventario no encontrado para producto {}",productoId);

                    return new ResourceNotFoundException("Inventario no encontrado");
                });

        if (inventario.getStock() < cantidad) {

            logger.warn("Stock insuficiente para producto {}",productoId);

            throw new BusinessException("Stock insuficiente");
        }

        inventario.setStock(inventario.getStock() - cantidad);

        inventario.setDisponible(inventario.getStock() > 0);

        inventario.setFechaActualizacion(LocalDateTime.now());

        inventarioRepository.save(inventario);

        MovimientoInventario movimiento = new MovimientoInventario();

        movimiento.setInventario(inventario);

        movimiento.setCantidad(cantidad);

        movimiento.setTipo("SALIDA");

        movimiento.setMotivo("Venta realizada");

        movimientoRepository.save(movimiento);

        logger.info("Stock descontado correctamente para producto {}",productoId);
    }
}