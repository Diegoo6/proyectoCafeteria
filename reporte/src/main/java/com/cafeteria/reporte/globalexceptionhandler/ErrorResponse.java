package com.cafeteria.reporte.globalexceptionhandler;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter

public class ErrorResponse {

    private LocalDate fecha;

    private Integer status;

    private String error;

    private String mensaje;

}
