package producto_service.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(description = "DTO para registrar o actualizar una categoría")
public class CategoriaRequestDTO {

    @Schema(description = "Nombre de la categoría",
            example = "Bebidas")
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 255, message = "El nombre no puede superar los 255 caracteres")
    private String nombre;
    
    @Schema(description = "Descripción de la categoría",
            example = "Productos líquidos para consumo")
    @Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
    private String descripcion;
}