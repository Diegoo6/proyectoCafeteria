package producto_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import producto_service.dto.CategoriaRequestDTO;
import producto_service.dto.CategoriaResponseDTO;
import producto_service.mapper.CategoriaMapper;
import producto_service.model.Categoria;
import producto_service.repository.CategoriaRepository;
import producto_service.repository.ProductoRepository;
import producto_service.exception.ResourceNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

 
    public List<CategoriaResponseDTO> listar() {
        return categoriaRepository.findAll()
                .stream()
                .map(CategoriaMapper::toResponse)
                .toList();
    }

  
    public CategoriaResponseDTO obtenerPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada"));

        return CategoriaMapper.toResponse(categoria);
    }


    public CategoriaResponseDTO guardar(CategoriaRequestDTO dto) {

        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }

        if (categoriaRepository.existsByNombre(dto.getNombre())) {
            throw new IllegalArgumentException("La categoria ya existe");
        }

        Categoria categoria = CategoriaMapper.toEntity(dto);
        return CategoriaMapper.toResponse(categoriaRepository.save(categoria));
    }

  
    public CategoriaResponseDTO actualizar(Long id, CategoriaRequestDTO dto) {

        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada"));

        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());

        return CategoriaMapper.toResponse(categoriaRepository.save(categoria));
    }

   
    public void eliminar(Long id) {

        if (!categoriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoria no encontrada");
        }

        if (productoRepository.existsByCategoriaId(id)) {
            throw new IllegalArgumentException("No puedes eliminar una categoria con productos asociados");
        }

        categoriaRepository.deleteById(id);
    }
}