package com.cafeteria.inventario_service.service;

import com.cafeteria.inventario_service.client.ProductoClient;
import com.cafeteria.inventario_service.dto.InventarioRequestDTO;
import com.cafeteria.inventario_service.dto.InventarioResponseDTO;
import com.cafeteria.inventario_service.exception.BusinessException;
import com.cafeteria.inventario_service.model.Inventario;
import com.cafeteria.inventario_service.repository.InventarioRepository;
import com.cafeteria.inventario_service.repository.MovimientoInventarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventarioServiceTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @Mock
    private MovimientoInventarioRepository movimientoRepository;

    @Mock
    private ProductoClient productoClient;

    @InjectMocks
    private InventarioService inventarioService;

    @Test
    @DisplayName("obtenerPorId debe retornar inventario cuando existe")
    void obtenerPorIdDebeRetornarInventarioCuandoExiste() {

        Inventario inventario = new Inventario(
                1L,
                10L,
                50,
                true,
                LocalDateTime.now()
        );

        when(inventarioRepository.findById(1L))
                .thenReturn(Optional.of(inventario));

        InventarioResponseDTO resultado =
                inventarioService.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(10L, resultado.getProductoId());
        assertEquals(50, resultado.getStock());

        verify(inventarioRepository).findById(1L);
    }

    @Test
    @DisplayName("guardar debe crear inventario y movimiento inicial")
    void guardarDebeCrearInventarioYMovimientoInicial() {

        InventarioRequestDTO dto = new InventarioRequestDTO();
        dto.setProductoId(1L);
        dto.setStock(100);
        dto.setDisponible(true);

        Inventario inventarioGuardado = new Inventario(
                1L,
                1L,
                100,
                true,
                LocalDateTime.now()
        );

        when(productoClient.obtenerProducto(1L))
                .thenReturn(new Object());

        when(inventarioRepository.existsByProductoId(1L))
                .thenReturn(false);

        when(inventarioRepository.save(any(Inventario.class)))
                .thenReturn(inventarioGuardado);

        InventarioResponseDTO resultado =
                inventarioService.guardar(dto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getProductoId());
        assertEquals(100, resultado.getStock());

        verify(inventarioRepository).save(any(Inventario.class));
        verify(movimientoRepository).save(any());
    }

    @Test
    @DisplayName("descontarStock debe reducir stock correctamente")
    void descontarStockDebeReducirStock() {

        Inventario inventario = new Inventario(
                1L,
                1L,
                50,
                true,
                LocalDateTime.now()
        );

        when(inventarioRepository.findByProductoId(1L))
                .thenReturn(Optional.of(inventario));

        inventarioService.descontarStock(1L, 10);

        assertEquals(40, inventario.getStock());

        verify(inventarioRepository).save(any(Inventario.class));
        verify(movimientoRepository).save(any());
    }

    @Test
    @DisplayName("guardar debe lanzar excepción cuando ya existe inventario")
    void guardarDebeLanzarExcepcionCuandoInventarioExiste() {

        InventarioRequestDTO dto = new InventarioRequestDTO();
        dto.setProductoId(1L);
        dto.setStock(50);
        dto.setDisponible(true);

        when(productoClient.obtenerProducto(1L))
                .thenReturn(new Object());

        when(inventarioRepository.existsByProductoId(1L))
                .thenReturn(true);

        assertThrows(
                BusinessException.class,
                () -> inventarioService.guardar(dto)
        );

        verify(inventarioRepository, never())
                .save(any(Inventario.class));
    }
}