package producto_service.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import producto_service.dto.ProductoRequestDTO;
import producto_service.dto.ProductoResponseDTO;
import producto_service.exception.ResourceNotFoundException;
import producto_service.model.Categoria;
import producto_service.model.Producto;
import producto_service.repository.CategoriaRepository;
import producto_service.repository.ProductoRepository;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    @DisplayName("obtenerPorId debe retornar producto cuando existe")
    void obtenerPorIdDebeRetornarProductoCuandoExiste() {

        // Given
        Categoria categoria = new Categoria(
                1L,
                "Bebidas",
                "Productos líquidos"
        );

        Producto producto = new Producto(
                1L,
                "Coca Cola",
                1500.0,
                "Bebida",
                categoria
        );

        when(productoRepository.findById(1L))
                .thenReturn(Optional.of(producto));

        // When
        ProductoResponseDTO resultado =
                productoService.obtenerPorId(1L);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Coca Cola", resultado.getNombre());

        verify(productoRepository).findById(1L);
    }

    @Test
    @DisplayName("guardar debe crear producto correctamente")
    void guardarDebeCrearProductoCorrectamente() {

        // Given
        ProductoRequestDTO dto = new ProductoRequestDTO();
        dto.setNombre("Coca Cola");
        dto.setPrecio(1500.0);
        dto.setDescripcion("Bebida");
        dto.setCategoriaId(1L);

        Categoria categoria = new Categoria(
                1L,
                "Bebidas",
                "Productos líquidos"
        );

        Producto productoGuardado = new Producto(
                1L,
                "Coca Cola",
                1500.0,
                "Bebida",
                categoria
        );

        when(productoRepository.existsByNombre("Coca Cola"))
                .thenReturn(false);

        when(categoriaRepository.findById(1L))
                .thenReturn(Optional.of(categoria));

        when(productoRepository.save(any(Producto.class)))
                .thenReturn(productoGuardado);

        // When
        ProductoResponseDTO resultado =
                productoService.guardar(dto);

        // Then
        assertNotNull(resultado);
        assertEquals("Coca Cola", resultado.getNombre());
        assertEquals(1500.0, resultado.getPrecio());

        verify(productoRepository).save(any(Producto.class));
    }

    @Test
    @DisplayName("guardar debe lanzar excepción cuando categoría no existe")
    void guardarDebeLanzarExcepcionCuandoCategoriaNoExiste() {

        // Given
        ProductoRequestDTO dto = new ProductoRequestDTO();
        dto.setNombre("Coca Cola");
        dto.setPrecio(1500.0);
        dto.setDescripcion("Bebida");
        dto.setCategoriaId(99L);

        when(productoRepository.existsByNombre("Coca Cola"))
                .thenReturn(false);

        when(categoriaRepository.findById(99L))
                .thenReturn(Optional.empty());

        // When + Then
        assertThrows(
                ResourceNotFoundException.class,
                () -> productoService.guardar(dto)
        );

        verify(productoRepository, never())
                .save(any(Producto.class));
    }

    @Test
    @DisplayName("eliminar debe eliminar producto cuando existe")
    void eliminarDebeEliminarProductoCuandoExiste() {

        // Given
        when(productoRepository.existsById(1L))
                .thenReturn(true);

        // When
        productoService.eliminar(1L);

        // Then
        verify(productoRepository).deleteById(1L);
    }
}