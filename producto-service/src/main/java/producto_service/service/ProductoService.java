package producto_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import producto_service.dto.ProductoRequestDTO;
import producto_service.dto.ProductoResponseDTO;
import producto_service.mapper.ProductoMapper;
import producto_service.model.Categoria;
import producto_service.model.Producto;
import producto_service.repository.CategoriaRepository;
import producto_service.repository.ProductoRepository;
import producto_service.exception.ResourceNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;


    public List<ProductoResponseDTO> listar() {
        return productoRepository.findAll()
                .stream()
                .map(ProductoMapper::toResponse)
                .toList();
    }

   
    public ProductoResponseDTO obtenerPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

        return ProductoMapper.toResponse(producto);
    }

  
    public ProductoResponseDTO guardar(ProductoRequestDTO dto) {

        if (dto.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }

        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }

        if (productoRepository.existsByNombre(dto.getNombre())) {
            throw new IllegalArgumentException("Ya existe un producto con ese nombre");
        }

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada"));

        Producto producto = ProductoMapper.toEntity(dto, categoria);
        Producto guardado = productoRepository.save(producto);

        return ProductoMapper.toResponse(guardado);
    }

  
    public ProductoResponseDTO actualizar(Long id, ProductoRequestDTO dto) {

        Producto existente = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

        if (dto.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada"));

        existente.setNombre(dto.getNombre());
        existente.setPrecio(dto.getPrecio());
        existente.setDescripcion(dto.getDescripcion());
        existente.setCategoria(categoria);

        return ProductoMapper.toResponse(productoRepository.save(existente));
    }


    public void eliminar(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Producto no encontrado");
        }
        productoRepository.deleteById(id);
    }

  
    public List<ProductoResponseDTO> buscarPorCategoria(Long categoriaId) {
        return productoRepository.findByCategoriaId(categoriaId)
                .stream()
                .map(ProductoMapper::toResponse)
                .toList();
    }
}