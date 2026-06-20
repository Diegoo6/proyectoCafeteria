package producto_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import producto_service.dto.ProductoRequestDTO;
import producto_service.dto.ProductoResponseDTO;
import producto_service.service.ProductoService;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import java.util.List;

@Tag(name = "Productos", description = "Operaciones relacionadas con los productos")
@RestController
@RequestMapping("api/v1/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @Operation(summary = "Listar todos los productos") 
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> listar() {
       
    List<ProductoResponseDTO> productos = productoService.listar();

    productos.forEach(producto -> {

        producto.add(
                linkTo(
                        methodOn(ProductoController.class)
                                .obtener(producto.getId())
                ).withSelfRel()
        );

        producto.add(
                linkTo(
                        methodOn(CategoriaController.class)
                                .obtener(producto.getCategoriaId())
                ).withRel("categoria")
        );
    });

    return ResponseEntity.ok(productos);
    }


    @Operation(summary = "Buscar producto por ID") 
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Producto encontrado"), 
                    @ApiResponse(responseCode = "404", description = "Producto no encontrado") })
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtener(@PathVariable Long id) {
        ProductoResponseDTO producto = productoService.obtenerPorId(id);

        producto.add(
                linkTo(
                        methodOn(ProductoController.class)
                                .obtener(id)
                ).withSelfRel()
        );
        producto.add(
                linkTo(
                        methodOn(ProductoController.class)
                                .listar()
                ).withRel("productos")
        );
        producto.add(
                linkTo(
                        methodOn(CategoriaController.class)
                                .obtener(producto.getCategoriaId())
                ).withRel("categoria")
        );

        return ResponseEntity.ok(producto);
    }


    @Operation(summary = "Crear un nuevo producto") 
    @ApiResponses({ @ApiResponse(responseCode = "201", description = "Producto creado correctamente"), 
                    @ApiResponse(responseCode = "400", description = "Datos inválidos") })
    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crear(@Valid @RequestBody ProductoRequestDTO dto) {

        ProductoResponseDTO producto = productoService.guardar(dto);

                producto.add(
                        linkTo(
                                methodOn(ProductoController.class)
                                        .obtener(producto.getId())
                        ).withSelfRel()
                );

                producto.add(
                        linkTo(
                                methodOn(ProductoController.class)
                                        .listar()
                        ).withRel("productos")
                );

                producto.add(
                        linkTo(
                                methodOn(CategoriaController.class)
                                        .obtener(producto.getCategoriaId())
                        ).withRel("categoria")
                );

    return ResponseEntity.status(HttpStatus.CREATED).body(producto);
    }


    @Operation(summary = "Actualizar un producto") 
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente"), 
                    @ApiResponse(responseCode = "404", description = "Producto no encontrado") })
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizar(@PathVariable Long id,@Valid @RequestBody ProductoRequestDTO dto) {

        ProductoResponseDTO producto =productoService.actualizar(id, dto);

        producto.add(
                linkTo(
                        methodOn(ProductoController.class)
                                .obtener(producto.getId())
                ).withSelfRel()
        );

        producto.add(
                linkTo(
                        methodOn(ProductoController.class)
                                .listar()
                ).withRel("productos")
        );

        producto.add(
                linkTo(
                        methodOn(CategoriaController.class)
                                .obtener(producto.getCategoriaId())
                ).withRel("categoria")
        );

        return ResponseEntity.ok(producto);
    }

    @Operation(summary = "Eliminar un producto") 
    @ApiResponses({ @ApiResponse(responseCode = "204", description = "Producto eliminado correctamente"), 
                    @ApiResponse(responseCode = "404", description = "Producto no encontrado") })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Buscar productos por categoría") 
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Productos encontrados"), 
                    @ApiResponse(responseCode = "404", description = "Categoría no encontrada") })
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<ProductoResponseDTO>> buscarPorCategoria(@PathVariable Long categoriaId) {

        List<ProductoResponseDTO> productos =
            productoService.buscarPorCategoria(categoriaId);

        productos.forEach(producto -> {

                producto.add(
                        linkTo(
                                methodOn(ProductoController.class)
                                        .obtener(producto.getId())
                        ).withSelfRel()
                );

                producto.add(
                        linkTo(
                                methodOn(CategoriaController.class)
                                        .obtener(producto.getCategoriaId())
                        ).withRel("categoria")
                );

                producto.add(
                        linkTo(
                                methodOn(ProductoController.class)
                                        .listar()
                        ).withRel("productos")
                );
        });

        return ResponseEntity.ok(productos);
    }
}