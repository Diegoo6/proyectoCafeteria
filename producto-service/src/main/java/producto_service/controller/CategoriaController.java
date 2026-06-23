package producto_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import producto_service.dto.CategoriaRequestDTO;
import producto_service.dto.CategoriaResponseDTO;
import producto_service.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

@Tag(name = "Categorías", description = "Operaciones relacionadas con las categorías de productos")
@RestController
@RequestMapping("api/v1/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;
    @Operation(summary = "Listar todas las categorías") 
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listar() {
        List<CategoriaResponseDTO> categorias = categoriaService.listar();

        categorias.forEach(categoria -> {

            categoria.add(
                    linkTo(
                            methodOn(CategoriaController.class)
                                    .obtener(categoria.getId())
                    ).withSelfRel()
            );

            categoria.add(
                    linkTo(
                            methodOn(ProductoController.class)
                                    .buscarPorCategoria(categoria.getId())
                    ).withRel("productos")
            );
        });

        return ResponseEntity.ok(categorias);
    }

    @Operation(summary = "Buscar categoría por ID") 
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Categoría encontrada"), 
                    @ApiResponse(responseCode = "404", description = "Categoría no encontrada") })
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> obtener(@PathVariable Long id) {
        CategoriaResponseDTO categoria = categoriaService.obtenerPorId(id);

        categoria.add(
                linkTo(
                        methodOn(CategoriaController.class)
                                .obtener(id)
                ).withSelfRel()
        );

        categoria.add(
                linkTo(
                        methodOn(CategoriaController.class)
                                .listar()
                ).withRel("categorias")
        );

        categoria.add(
                linkTo(
                        methodOn(ProductoController.class)
                                .buscarPorCategoria(id)
                ).withRel("productos")
        );

        return ResponseEntity.ok(categoria);
    }


    @Operation(summary = "Crear una nueva categoría") 
    @ApiResponses({ @ApiResponse(responseCode = "201", description = "Categoría creada correctamente"), 
                    @ApiResponse(responseCode = "400", description = "Datos inválidos") })
    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> crear(@Valid @RequestBody CategoriaRequestDTO dto) {

        CategoriaResponseDTO categoria = categoriaService.guardar(dto);

        categoria.add(
                linkTo(
                        methodOn(CategoriaController.class)
                                .obtener(categoria.getId())
                ).withSelfRel()
        );

        categoria.add(
                linkTo(
                        methodOn(CategoriaController.class)
                                .listar()
                ).withRel("categorias")
        );

        categoria.add(
                linkTo(
                        methodOn(ProductoController.class)
                                .buscarPorCategoria(categoria.getId())
                ).withRel("productos")
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
    }


    @Operation(summary = "Actualizar una categoría") 
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Categoría actualizada correctamente"), 
                    @ApiResponse(responseCode = "404", description = "Categoría no encontrada") })
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> actualizar(@PathVariable Long id,@Valid @RequestBody CategoriaRequestDTO dto) {

        CategoriaResponseDTO categoria = categoriaService.actualizar(id, dto);

        categoria.add(
                linkTo(
                        methodOn(CategoriaController.class)
                                .obtener(categoria.getId())
                ).withSelfRel()
        );

        categoria.add(
                linkTo(
                        methodOn(CategoriaController.class)
                                .listar()
                ).withRel("categorias")
        );

        categoria.add(
                linkTo(
                        methodOn(ProductoController.class)
                                .buscarPorCategoria(categoria.getId())
                ).withRel("productos")
        );

        return ResponseEntity.ok(categoria);
    }


    @Operation(summary = "Eliminar una categoría") 
    @ApiResponses({ @ApiResponse(responseCode = "204", description = "Categoría eliminada correctamente"), 
                    @ApiResponse(responseCode = "404", description = "Categoría no encontrada") })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        categoriaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}