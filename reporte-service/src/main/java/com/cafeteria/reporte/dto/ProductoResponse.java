package com.cafeteria.reporte.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ProductoResponse {

    private Long id;

    private String nombre;

    private Double precio;

    private String descripcion;

    private String categoriaNombre;

}
