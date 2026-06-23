package com.example.empleado_service.model;

import java.time.LocalDate;

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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "empleados")
public class Empleado {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false, length = 50)
    private String nombre;

    @Column (nullable = false, length = 50)
    private String apellido;

    @Column (nullable = false, length = 20)
    private String telefono;

    @Column (nullable = false, unique = true, length = 100)
    private String correo;

    @Column (nullable = false)
    private LocalDate fechaNacimiento;

    @ManyToOne
    @JoinColumn (name = "cargo_id", nullable = false)
    private Cargo cargo;

    @Column (nullable = false, unique = true)
    private Long usuarioId;
    
}
