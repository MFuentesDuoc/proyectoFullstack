package com.pacientes_service.pacientes_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Table(name = "paciente")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, length = 13, nullable = false)
    private String rut;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    @Column(nullable = false)
    private String email;

    @Column(nullable = true)
    private String telefono;

    @Column(nullable = true)
    private String direccion;

    // --- CAMBIO CLAVE PARA LA CONEXIÓN ---
    @Column(name = "medico_id", nullable = true)
    private Integer medicoId;
    // Este ID vive en la base de datos de Pacientes (Laragon)
    // pero hace referencia a un registro en la base de datos de Médicos.
}