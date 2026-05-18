package com.cafeteria.venta.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class EmpleadoResponse {

    private Long id;

    private String nombre;

    private String apellido;

    private String tipoCargo;

}