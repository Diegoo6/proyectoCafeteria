package producto_service.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import producto_service.dto.CategoriaRequestDTO;
import producto_service.dto.CategoriaResponseDTO;
import producto_service.exception.ResourceNotFoundException;
import producto_service.mapper.CategoriaMapper;
import producto_service.model.Categoria;
import producto_service.repository.CategoriaRepository;
import producto_service.repository.ProductoRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private static final Logger logger = LoggerFactory.getLogger(CategoriaService.class);

    private final CategoriaRepository categoriaRepository;

    private final ProductoRepository productoRepository;

    public List<CategoriaResponseDTO> listar() {

        logger.info("Listando todas las categorias");

        return categoriaRepository.findAll()
                .stream()
                .map(CategoriaMapper::toResponse)
                .toList();
    }

    public CategoriaResponseDTO obtenerPorId(Long id) {

        logger.info("Buscando categoria con id {}", id);

        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> {

                    logger.error("Categoria {} no encontrada", id);

                    return new ResourceNotFoundException("Categoria no encontrada");
                });

        return CategoriaMapper.toResponse(categoria);
    }

    public CategoriaResponseDTO guardar(CategoriaRequestDTO dto) {

        logger.info("Intentando crear categoria {}",dto.getNombre());

        if (dto.getNombre() == null || dto.getNombre().isBlank()) {

            logger.warn("Nombre de categoria vacio");

            throw new IllegalArgumentException("El nombre es obligatorio");
        }

        if (categoriaRepository.existsByNombre(dto.getNombre())) {

            logger.warn("La categoria {} ya existe",dto.getNombre());

            throw new IllegalArgumentException("La categoria ya existe");
        }

        Categoria categoria = CategoriaMapper.toEntity(dto);

        Categoria guardada = categoriaRepository.save(categoria);

        logger.info("Categoria creada correctamente con id {}",guardada.getId());

        return CategoriaMapper.toResponse(guardada);
    }

    public CategoriaResponseDTO actualizar(Long id,CategoriaRequestDTO dto) {

        logger.info("Actualizando categoria {}", id);

        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> {

                    logger.error("Categoria {} no encontrada", id);

                    return new ResourceNotFoundException("Categoria no encontrada");
                });

        categoria.setNombre(dto.getNombre());

        categoria.setDescripcion(dto.getDescripcion());

        Categoria actualizada = categoriaRepository.save(categoria);

        logger.info("Categoria {} actualizada correctamente",id);

        return CategoriaMapper.toResponse(actualizada);
    }

    public void eliminar(Long id) {

        logger.info("Eliminando categoria {}", id);

        if (!categoriaRepository.existsById(id)) {

            logger.error("Categoria {} no encontrada", id);

            throw new ResourceNotFoundException("Categoria no encontrada");
        }

        if (productoRepository.existsByCategoriaId(id)) {

            logger.warn("No se puede eliminar categoria {} porque tiene productos asociados",id);

            throw new IllegalArgumentException("No puedes eliminar una categoria con productos asociados");
        }

        categoriaRepository.deleteById(id);

        logger.info("Categoria {} eliminada correctamente",id);
    }
}