package com.cafeteria.venta.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name= "venta")

public class Venta {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name= "fecha_venta", nullable= false)
    private LocalDate fechaVenta;

    @Column(nullable= false)
    private Double total;

    @Column(name= "estado_de_pago", nullable= false, length= 25)
    private String estadoDePago;                // puede ser pagado, pendiente o cancelado

    @Column(name= "metodo_de_pago", nullable = false, length= 25)
    private String metodoDePago;

    @Column(length= 225)
    private String observacion;

    @Column(name= "empleado_id", nullable= false)
    private Long empleadoId;

}
