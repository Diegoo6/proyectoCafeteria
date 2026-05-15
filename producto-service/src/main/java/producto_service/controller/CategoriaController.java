package producto_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import producto_service.dto.CategoriaRequestDTO;
import producto_service.dto.CategoriaResponseDTO;
import producto_service.service.CategoriaService;

import java.util.List;

@RestController
@RequestMapping("api/v1/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

   
    @GetMapping
    public List<CategoriaResponseDTO> listar() {
        return categoriaService.listar();
    }

    
    @GetMapping("/{id}")
    public CategoriaResponseDTO obtener(@PathVariable Long id) {
        return categoriaService.obtenerPorId(id);
    }

    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoriaResponseDTO crear(@Valid @RequestBody CategoriaRequestDTO dto) {
        return categoriaService.guardar(dto);
    }

    
    @PutMapping("/{id}")
    public CategoriaResponseDTO actualizar(@PathVariable Long id, @Valid @RequestBody CategoriaRequestDTO dto) {
        return categoriaService.actualizar(id, dto);
    }

    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        categoriaService.eliminar(id);
    }
}