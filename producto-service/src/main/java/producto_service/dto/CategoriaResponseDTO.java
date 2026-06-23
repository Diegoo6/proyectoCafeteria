package producto_service.dto;

import org.springframework.hateoas.RepresentationModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Schema(description = "Respuesta de una categoría")
public class CategoriaResponseDTO  extends RepresentationModel<CategoriaResponseDTO>{
    @Schema(example = "1")
    private Long id;
    @Schema(example = "Bebidas")
    private String nombre;
    @Schema(example = "Productos líquidos para consumo")
    private String descripcion;

}