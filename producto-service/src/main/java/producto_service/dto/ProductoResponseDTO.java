package producto_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductoResponseDTO {

    private Long id;
    private String nombre;
    private double precio;
    private String descripcion;
    private String categoriaNombre;

}