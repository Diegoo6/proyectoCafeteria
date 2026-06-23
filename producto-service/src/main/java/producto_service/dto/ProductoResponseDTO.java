package producto_service.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Respuesta de un producto")
public class ProductoResponseDTO extends RepresentationModel<ProductoResponseDTO>{

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Coca Cola 1.5L")
    private String nombre;

    @Schema(example = "2500")
    private double precio;

    @Schema(example = "Bebida gaseosa retornable")
    private String descripcion;

    @Schema(example = "Bebidas")
    private String categoriaNombre;

    @Schema(example = "1")
    private Long categoriaId;

}