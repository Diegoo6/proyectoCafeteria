package producto_service.mapper;

import producto_service.dto.CategoriaRequestDTO;
import producto_service.dto.CategoriaResponseDTO;
import producto_service.model.Categoria;

public class CategoriaMapper {

    
    public static Categoria toEntity(CategoriaRequestDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());
        return categoria;
    }

   
    public static CategoriaResponseDTO toResponse(Categoria categoria) {
        return new CategoriaResponseDTO(
                categoria.getId(),
                categoria.getNombre(),
                categoria.getDescripcion()
        );
    }
}