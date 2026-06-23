package producto_service.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(description = "DTO para registrar o actualizar un producto")
public class ProductoRequestDTO {

     @Schema(description = "Nombre del producto",
            example = "Coca Cola 1.5L")
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 255, message = "El nombre no puede superar los 255 caracteres")
    private String nombre;

    @Schema(description = "Precio del producto",
            example = "2500")
    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a 0")
    private Double precio;

    @Schema(description = "Descripción del producto",
            example = "Bebida gaseosa retornable")
    @Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
    private String descripcion;

    @Schema(description = "ID de la categoría asociada",
            example = "1")
    @NotNull(message = "Debe asignar una categoria")
    private Long categoriaId;
}