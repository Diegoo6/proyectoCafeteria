package producto_service.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import producto_service.dto.ProductoRequestDTO;
import producto_service.dto.ProductoResponseDTO;
import producto_service.exception.ResourceNotFoundException;
import producto_service.mapper.ProductoMapper;
import producto_service.model.Categoria;
import producto_service.model.Producto;
import producto_service.repository.CategoriaRepository;
import producto_service.repository.ProductoRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private static final Logger logger = LoggerFactory.getLogger(ProductoService.class);

    private final ProductoRepository productoRepository;

    private final CategoriaRepository categoriaRepository;

    public List<ProductoResponseDTO> listar() {

        logger.info("Listando todos los productos");

        return productoRepository.findAll()
                .stream()
                .map(ProductoMapper::toResponse)
                .toList();
    }

    public ProductoResponseDTO obtenerPorId(Long id) {

        logger.info("Buscando producto con id {}", id);

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> {

                    logger.error("Producto {} no encontrado", id);

                    return new ResourceNotFoundException("Producto no encontrado");
                });

        return ProductoMapper.toResponse(producto);
    }

    public ProductoResponseDTO guardar(ProductoRequestDTO dto) {

        logger.info("Intentando crear producto {}",dto.getNombre());

        if (dto.getPrecio() <= 0) {

            logger.warn("Precio inválido para producto {}",dto.getNombre());

            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }

        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {

            logger.warn("Nombre de producto vacío");

            throw new IllegalArgumentException("El nombre es obligatorio");
        }

        if (productoRepository.existsByNombre(dto.getNombre())) {

            logger.warn("Ya existe un producto con nombre {}",dto.getNombre());

            throw new IllegalArgumentException("Ya existe un producto con ese nombre");
        }

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> {

                    logger.error("Categoria {} no encontrada",dto.getCategoriaId());

                    return new ResourceNotFoundException("Categoria no encontrada");
                });

        Producto producto = ProductoMapper.toEntity(dto, categoria);

        Producto guardado = productoRepository.save(producto);

        logger.info("Producto creado correctamente con id {}",guardado.getId());

        return ProductoMapper.toResponse(guardado);
    }

    public ProductoResponseDTO actualizar(Long id,ProductoRequestDTO dto) {

        logger.info("Actualizando producto {}", id);

        Producto existente = productoRepository.findById(id)
                .orElseThrow(() -> {

                    logger.error("Producto {} no encontrado", id);

                    return new ResourceNotFoundException("Producto no encontrado");
                });

        if (dto.getPrecio() <= 0) {

            logger.warn("Precio inválido para producto {}",dto.getNombre());

            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> {

                    logger.error("Categoria {} no encontrada",dto.getCategoriaId());

                    return new ResourceNotFoundException("Categoria no encontrada");
                });

        existente.setNombre(dto.getNombre());

        existente.setPrecio(dto.getPrecio());

        existente.setDescripcion(dto.getDescripcion());

        existente.setCategoria(categoria);

        Producto actualizado = productoRepository.save(existente);

        logger.info("Producto {} actualizado correctamente",id);

        return ProductoMapper.toResponse(actualizado);
    }

    public void eliminar(Long id) {

        logger.info("Eliminando producto {}", id);

        if (!productoRepository.existsById(id)) {

            logger.error("Producto {} no encontrado", id);

            throw new ResourceNotFoundException("Producto no encontrado");
        }

        productoRepository.deleteById(id);

        logger.info("Producto {} eliminado correctamente",id);
    }

    public List<ProductoResponseDTO> buscarPorCategoria(Long categoriaId) {

        logger.info("Buscando productos para categoria {}",categoriaId);

        return productoRepository.findByCategoriaId(categoriaId)
                .stream()
                .map(ProductoMapper::toResponse)
                .toList();
    }
}