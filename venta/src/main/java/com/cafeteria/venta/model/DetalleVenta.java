package com.cafeteria.venta.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name= "detalle_venta")

public class DetalleVenta {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name= "producto_id", nullable= false)
    private Long productoId;

    @Column(nullable= false)
    private Integer cantidad;

    @Column(name= "precio_unitario", nullable= false)
    private Double precioUnitario;

    @Column(nullable= false)
    private Double subtotal;

    @ManyToOne
    @JoinColumn(name= "venta_id")
    private Venta venta;

}
