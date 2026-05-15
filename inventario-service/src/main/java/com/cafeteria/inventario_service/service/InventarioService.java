package com.cafeteria.inventario_service.service;

import com.cafeteria.inventario_service.client.ProductoClient;
import com.cafeteria.inventario_service.dto.InventarioRequestDTO;
import com.cafeteria.inventario_service.dto.InventarioResponseDTO;
import com.cafeteria.inventario_service.exception.BusinessException;
import com.cafeteria.inventario_service.exception.ResourceNotFoundException;
import com.cafeteria.inventario_service.mapper.InventarioMapper;
import com.cafeteria.inventario_service.model.Inventario;
import com.cafeteria.inventario_service.repository.InventarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventarioService {

    private final InventarioRepository inventarioRepository;

    // 🔥 FEIGN CLIENT
    private final ProductoClient productoClient;

    // 📄 LISTAR TODOS
    public List<InventarioResponseDTO> listar() {

        return inventarioRepository.findAll()
                .stream()
                .map(InventarioMapper::toResponse)
                .toList();
    }

    // 🔍 OBTENER POR ID
    public InventarioResponseDTO obtenerPorId(Long id) {

        Inventario inventario = inventarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Inventario no encontrado"
                        ));

        return InventarioMapper.toResponse(inventario);
    }

    // 💾 CREAR INVENTARIO
    public InventarioResponseDTO guardar(
            InventarioRequestDTO dto) {

        // 🔥 VALIDAR SI EL PRODUCTO EXISTE
        try {

            productoClient.obtenerProducto(
                    dto.getProductoId()
            );

        } catch (Exception e) {

            throw new BusinessException(
                    "El producto no existe"
            );
        }

        // 🔥 EVITAR DUPLICADOS
        if (inventarioRepository
                .existsByProductoId(dto.getProductoId())) {

            throw new BusinessException(
                    "Ya existe inventario para este producto"
            );
        }

        Inventario inventario =
                InventarioMapper.toEntity(dto);

        // 🔥 DISPONIBILIDAD AUTOMÁTICA
        inventario.setDisponible(
                inventario.getStock() > 0
        );

        Inventario guardado =
                inventarioRepository.save(inventario);

        return InventarioMapper.toResponse(guardado);
    }

    // ✏️ ACTUALIZAR
    public InventarioResponseDTO actualizar(
            Long id,
            InventarioRequestDTO dto) {

        Inventario inventario =
                inventarioRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Inventario no encontrado"
                                ));

        // 🔥 VALIDAR PRODUCTO
        try {

            productoClient.obtenerProducto(
                    dto.getProductoId()
            );

        } catch (Exception e) {

            throw new BusinessException(
                    "El producto no existe"
            );
        }

        inventario.setProductoId(dto.getProductoId());
        inventario.setStock(dto.getStock());

        // 🔥 DISPONIBILIDAD AUTOMÁTICA
        inventario.setDisponible(
                dto.getStock() > 0
        );

        Inventario actualizado =
                inventarioRepository.save(inventario);

        return InventarioMapper.toResponse(actualizado);
    }

    // 🗑️ ELIMINAR
    public void eliminar(Long id) {

        if (!inventarioRepository.existsById(id)) {

            throw new ResourceNotFoundException(
                    "Inventario no encontrado"
            );
        }

        inventarioRepository.deleteById(id);
    }

    // 🔎 BUSCAR POR PRODUCTO
    public InventarioResponseDTO
    buscarPorProductoId(Long productoId) {

        Inventario inventario =
                inventarioRepository
                        .findByProductoId(productoId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Inventario no encontrado"
                                ));

        return InventarioMapper.toResponse(inventario);
    }
}